package P2P.PeerPeer.Message;

import java.io.DataInputStream;

public abstract class Message {

	/**
	 * Size of "type" field: byte (1 bytes)
	 */
	protected static final int FIELD_TYPE_BYTES = 1;

	/**
	 * Size of "hash" field: byte (20 bytes)
	 */
	protected static final int FIELD_HASH_BYTES = 20;

	/**
	 * Size of "n_chunks" field: byte (4 bytes)
	 */
	protected static final int FIELD_N_CHUNKS_BYTES = 4;

	/**
	 * Size of "index" field: byte (4 bytes)
	 */
	protected static final int FIELD_INDEX_BYTES = 4;

	//Size of the messages
	public static final int SIZE_ERROR = FIELD_TYPE_BYTES;
	public static final int SIZE_REQ_LIST = FIELD_TYPE_BYTES + FIELD_HASH_BYTES;
	public static final int SIZE_REQ_DATA = FIELD_TYPE_BYTES + FIELD_INDEX_BYTES;

	public static final int MAX_SIZE_LIST = FIELD_TYPE_BYTES + FIELD_N_CHUNKS_BYTES + FIELD_INDEX_BYTES * 2 ^ 31;

	//Type of the messages
	public static final int TYPE_REQ_LIST = 1;
	public static final int TYPE_LIST = 2;
	public static final int TYPE_REQ_DATA = 3;
	public static final int TYPE_DATA = 4;
	public static final int TYPE_ERROR = 5;

	private int type;

	public abstract byte[] toByteArray();

	public int getType() {
		return type;
	}

	protected void setType(int type) {
		this.type = type;
	}

	public static MessageHash makeReqList(String hash) {
		return new MessageHash(hash);
	}

	public static MessageHash makeReqList(byte[] array) {
		return new MessageHash(array);
	}

	public static MessageChunkList makeChunkList(int nChunk) {
		return new MessageChunkList(nChunk);
	}

	public static MessageChunkList makeChunkList(int nChunk, int index[]) {
		return new MessageChunkList(nChunk, index);
	}

	public static MessageChunkList makeChunkList(DataInputStream dis) {
		MessageChunkList m = new MessageChunkList();
		if (m.fromStream(dis))
			return m;
		else
			return null;
	}

	public static MessageChunk makeReqData(int index) {
		return new MessageChunk(index);
	}

	public static MessageChunk makeReqData(byte[] array) {
		return new MessageChunk(array);
	}

	public static MessageData makeChunkData(byte[] dataArray, int index) {
		MessageData m = new MessageData(index);
		if (m.fromByteArray(dataArray))
			return m;
		else
			return null;
	}

	public static MessageData makeChunkData(DataInputStream dis, int chunkSize) {
		MessageData m = new MessageData();
		if (m.fromStream(dis, chunkSize))
			return m;
		else
			return null;
	}

	public static MessageError makeError(byte[] array) {
		return new MessageError(array);
	}

	public static MessageError makeError() {
		return new MessageError();
	}
}