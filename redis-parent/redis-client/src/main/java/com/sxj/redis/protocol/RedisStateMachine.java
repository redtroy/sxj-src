// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.protocol;

import static com.sxj.redis.protocol.Charsets.buffer;
import static com.sxj.redis.protocol.RedisStateMachine.State.Type.BULK;
import static com.sxj.redis.protocol.RedisStateMachine.State.Type.BYTES;
import static com.sxj.redis.protocol.RedisStateMachine.State.Type.ERROR;
import static com.sxj.redis.protocol.RedisStateMachine.State.Type.INTEGER;
import static com.sxj.redis.protocol.RedisStateMachine.State.Type.MULTI;
import static com.sxj.redis.protocol.RedisStateMachine.State.Type.SINGLE;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import com.sxj.redis.RedisException;

/**
 * State machine that decodes redis server responses encoded according to the <a
 * href="http://redis.io/topics/protocol">Unified Request Protocol</a>.
 *
 * @author Will Glozer
 */
public class RedisStateMachine<K, V> {
	private static final ByteBuffer QUEUED = buffer("QUEUED");

	static class State {
		enum Type {
			SINGLE, ERROR, INTEGER, BULK, MULTI, BYTES
		}

		Type type = null;

		int count = -1;
	}

	private LinkedList<State> stack;

	/**
	 * Initialize a new instance.
	 */
	public RedisStateMachine() {
		stack = new LinkedList<State>();
	}

	/**
	 * Attempt to decode a redis response and return a flag indicating whether a
	 * complete response was read.
	 *
	 * @param buffer
	 *            Buffer containing data from the server.
	 * @param output
	 *            Current command output.
	 *
	 * @return true if a complete response was read.
	 */
	public boolean decode(ByteBuf buffer, CommandOutput<K, V, ?> output) {
		int length, end;
		ByteBuffer bytes;

		if (stack.isEmpty()) {
			stack.add(new State());
		}

		if (output == null) {
			return stack.isEmpty();
		}

		while (!stack.isEmpty()) {
			State state = stack.peek();

			if (state.type == null) {
				if (!buffer.isReadable())
					break;
				state.type = readReplyType(buffer);
				buffer.markReaderIndex();
			}

			switch (state.type) {
			case SINGLE:
				if ((bytes = readLine(buffer)) == null)
					break;
				if (!QUEUED.equals(bytes)) {
					output.set(bytes);
				}
				break;
			case ERROR:
				if ((bytes = readLine(buffer)) == null)
					break;
				output.setError(bytes);
				break;
			case INTEGER:
				if ((end = findLineEnd(buffer)) == -1)
					break;
				output.set(readLong(buffer, buffer.readerIndex(), end));
				break;
			case BULK:
				if ((end = findLineEnd(buffer)) == -1)
					break;
				length = (int) readLong(buffer, buffer.readerIndex(), end);
				if (length == -1) {
					output.set(null);
				} else {
					state.type = BYTES;
					state.count = length + 2;
					buffer.markReaderIndex();
					continue;
				}
				break;
			case MULTI:
				if (state.count == -1) {
					if ((end = findLineEnd(buffer)) == -1)
						break;
					length = (int) readLong(buffer, buffer.readerIndex(), end);
					state.count = length;
					buffer.markReaderIndex();
				}

				if (state.count <= 0)
					break;

				state.count--;
				stack.addFirst(new State());
				continue;
			case BYTES:
				if ((bytes = readBytes(buffer, state.count)) == null)
					break;
				output.set(bytes);
			}

			buffer.markReaderIndex();
			stack.remove();
			output.complete(stack.size());
		}

		return stack.isEmpty();
	}

	private int findLineEnd(ByteBuf buffer) {
		int start = buffer.readerIndex();
		int index = buffer.indexOf(start, buffer.writerIndex(), (byte) '\n');
		return (index > 0 && buffer.getByte(index - 1) == '\r') ? index : -1;
	}

	private State.Type readReplyType(ByteBuf buffer) {

		// String string = buffer.toString(Charset.forName("UTF-8"));
		// System.out.println("---------------" + string);
		byte readByte = buffer.readByte();
		switch (readByte) {
		case '+':
			return SINGLE;
		case '-':
			return ERROR;
		case ':':
			return INTEGER;
		case '$':
			return BULK;
		case '*':
			return MULTI;
		default:
			// return BULK;
			throw new RedisException("Invalid first byte");
		}
	}

	private long readLong(ByteBuf buffer, int start, int end) {
		long value = 0;

		boolean negative = buffer.getByte(start) == '-';
		int offset = negative ? start + 1 : start;
		while (offset < end - 1) {
			int digit = buffer.getByte(offset++) - '0';
			value = value * 10 - digit;
		}
		if (!negative)
			value = -value;
		buffer.readerIndex(end + 1);

		return value;
	}

	private ByteBuffer readLine(ByteBuf buffer) {
		ByteBuffer bytes = null;
		int end = findLineEnd(buffer);
		if (end > -1) {
			int start = buffer.readerIndex();
			bytes = buffer.nioBuffer(start, end - start - 1);
			buffer.readerIndex(end + 1);
		}
		return bytes;
	}

	private ByteBuffer readBytes(ByteBuf buffer, int count) {
		ByteBuffer bytes = null;
		if (buffer.readableBytes() >= count) {
			bytes = buffer.nioBuffer(buffer.readerIndex(), count - 2);
			buffer.readerIndex(buffer.readerIndex() + count);
		}
		return bytes;
	}
}
