package third.rewrite.fastdfs;

class FieldDefinition {
	
	private int offset;
	private int size;

	FieldDefinition(int offset, int size) {
		this.offset = offset;
		this.size = size;
	}

	/**
	 * @return the offset
	 */
	int getOffset() {
		return offset;
	}

	/**
	 * @return the size
	 */
	int getSize() {
		return size;
	}
}
