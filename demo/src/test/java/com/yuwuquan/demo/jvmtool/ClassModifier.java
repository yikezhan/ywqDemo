package com.yuwuquan.demo.jvmtool;

/**
 * 对测试类class文件的字节数组执行替换，将oldStr替换成newStr
 */
public class ClassModifier {
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;
    private static final int CONSTANT_UTF8_info = 1;
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, 5, -1, 5, 9, 9, 3, 3, 5, 5, 5, 5};
    private final int u1 = 1;
    private final int u2 = 2;
    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    public byte[] modiftyUTF8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for (int i = 0; i < cpc; i++) {
            //取出CONSTANT_UTF8_info中标志部分
            int tag = ByteUtils.byte2Int(classByte, offset, u1);
            //判断是否为CONSTANT_UTF8_info数据类型
            if (tag == CONSTANT_UTF8_info) {
                //取出CONSTANT_UTF8_info中字符串的长度
                int len = ByteUtils.byte2Int(classByte, offset + u1, u2);
                offset += (u1 + u2);
                //取出CONSTANT_UTF8_info中的字符串部分
                String str = ByteUtils.bytes2String(classByte, offset, len);
                //通过字符串部分比较是否为需要修改的CONSTANT_UTF8_info
                if (str.equalsIgnoreCase(oldStr)) {
                    //将新字符串的值打散成字节数组
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    //将表示字符串长度值的两个字节分别以16进制的形式装在字节数组中
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
                    //将CONSTANT_UTF8_info中表示length部分进行替换
                    classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
                    //将CONSTANT_UTF8_info中字符串部分进行替换
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
                    return classByte;
                    //如不是需要修改的CONSTANT_UTF8_info，则跳过这个类型，接着循环
                } else {
                    offset += len;
                }
                //如果不是CONSTANT_UTF8_info数据类型，根据tag跳转CONSTANT_ITEM_LENGTH中定义的字节数
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classByte;
    }

    public int getConstantPoolCount() {
        return ByteUtils.byte2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}