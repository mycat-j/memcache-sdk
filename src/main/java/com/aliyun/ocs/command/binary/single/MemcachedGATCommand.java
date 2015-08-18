package com.aliyun.ocs.command.binary.single;

import com.aliyun.ocs.OcsException;
import com.aliyun.ocs.protocol.memcached.binary.AbstractBinaryMemcachedMessage;
import com.aliyun.ocs.protocol.memcached.binary.BinaryMemcachedMessage;
import com.aliyun.ocs.protocol.memcached.binary.BinaryMemcachedMessageHeader;
import com.aliyun.ocs.protocol.memcached.binary.BinaryMemcachedRequestMessageHeader;
import com.aliyun.ocs.protocol.memcached.binary.content.BinaryContent;
import com.aliyun.ocs.protocol.memcached.binary.content.BinaryContentByteArray;
import com.aliyun.ocs.protocol.memcached.binary.extras.BinaryExtras;
import com.aliyun.ocs.protocol.memcached.binary.extras.BinaryExtras_Flags;
import com.aliyun.ocs.protocol.memcached.binary.lazydecoder.LazyDecoderFactory;
import com.aliyun.ocs.rpc.OcsRpc;

public class MemcachedGATCommand extends MemcachedGetCommand {
	protected int exper;

	public MemcachedGATCommand(OcsRpc rpc, byte opcode, String key, int exper) {
		super(rpc, opcode, key);
		this.exper = exper;
		this.lazyDecoder = LazyDecoderFactory.LAZY_DECODER_INTERGER_BYTEARRAY;
	}

	public BinaryMemcachedMessage buildMessage() throws OcsException {
		byte[] bkey = trans.encodeKey(key);
		if (bkey == null || bkey.length == 0) {
			throw new OcsException("null key");
		}
		BinaryContent content = new BinaryContentByteArray(bkey);
		BinaryExtras extras = new BinaryExtras_Flags(exper);
		BinaryMemcachedMessageHeader header = new BinaryMemcachedRequestMessageHeader((short) bkey.length, (byte) extras.getSize(), (byte) 0,
				content.getSize(), 0l);
		BinaryMemcachedMessage message = new AbstractBinaryMemcachedMessage(header, extras, content);
		message.setOpcode(opcode);
		return message;
	}
}
