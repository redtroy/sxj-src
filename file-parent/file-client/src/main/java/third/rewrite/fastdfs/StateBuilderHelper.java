package third.rewrite.fastdfs;

import java.nio.charset.Charset;
import java.util.Date;

import third.rewrite.fastdfs.proto.BytesUtil;

class StateBuilderHelper {

	static String stringValue(byte[] bs, int offset, FieldDefinition fd,
			Charset charset) {
		return (new String(bs, offset + fd.getOffset(), fd.getSize(), charset))
				.trim();
	}

	static long longValue(byte[] bs, int offset, FieldDefinition fd) {
		return BytesUtil.buff2long(bs, offset + fd.getOffset());
	}

	static int intValue(byte[] bs, int offset, FieldDefinition fd) {
		return (int) BytesUtil.buff2long(bs, offset + fd.getOffset());
	}

	static byte byteValue(byte[] bs, int offset, FieldDefinition fd) {
		return bs[offset + fd.getOffset()];
	}

	static boolean booleanValue(byte[] bs, int offset, FieldDefinition fd) {
		return bs[offset + fd.getOffset()] != 0;
	}

	static Date dateValue(byte[] bs, int offset, FieldDefinition fd) {
		return new Date(BytesUtil.buff2long(bs, offset + fd.getOffset()) * 1000);
	}

}
