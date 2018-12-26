package main.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile 测试程序
 * 当操作的文件是存储在本地的基础存储设备上时(如硬盘, NandFlash等)，"rws" 或 "rwd", "rw" 才有区别。
 * 当模式是 "rws" 并且 操作的是基础存储设备上的文件；那么，每次“更改文件内容[如write()写入数据]” 或 “修改文件元数据(如文件的mtime)”时，都会将这些改变同步到基础存储设备上。
 * 当模式是 "rwd" 并且 操作的是基础存储设备上的文件；那么，每次“更改文件内容[如write()写入数据]”时，都会将这些改变同步到基础存储设备上。
 * 当模式是 "rw" 并且 操作的是基础存储设备上的文件；那么，关闭文件时，会将“文件内容的修改”同步到基础存储设备上。至于，“更改文件内容”时，是否会立即同步，取决于系统底层实现
 * @author fanwei
 *
 */
public class RandomAccessFileTest
{
    private static final String FileName = "file.txt";

    public static void main(String[] args)
    {
        // 若文件“file.txt”存在，则删除该文件。
        File file = new File(FileName);
        if (file.exists())
            file.delete();

        testCreateWrite();
        testAppendWrite();
        testRead();
    }

    /**
     * 若“file.txt”不存在的话，则新建文件，并向文件中写入内容
     */
    private static void testCreateWrite()
    {
        try
        {
            // 创建文件“file.txt”对应File对象
            File file = new File(FileName);
            // 创建文件“file.txt”对应的RandomAccessFile对象
            RandomAccessFile raf = new RandomAccessFile(file, "rw");

            // 向“文件中”写入26个字母+回车
            raf.writeChars("abcdefghijklmnopqrstuvwxyz\n");
            // 向“文件中”写入"9876543210"+回车
            raf.writeChars("9876543210\n");

            raf.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向文件末尾追加内容
     */
    private static void testAppendWrite()
    {
        try
        {
            // 创建文件“file.txt”对应File对象
            File file = new File(FileName);
            // 创建文件“file.txt”对应的RandomAccessFile对象
            RandomAccessFile raf = new RandomAccessFile(file, "rw");

            // 获取文件长度
            long fileLen = raf.length();
            // 将位置定位到“文件末尾”
            raf.seek(fileLen);

            // 以下向raf文件中写数据  
            raf.writeBoolean(true); // 占1个字节  
            raf.writeByte(0x41); // 占1个字节  
            raf.writeChar('a'); // 占2个字节  
            raf.writeShort(0x3c3c); // 占2个字节  
            raf.writeInt(0x75); // 占4个字节  
            raf.writeLong(0x1234567890123456L); // 占8个字节  
            raf.writeFloat(4.7f); // 占4个字节  
            raf.writeDouble(8.256);// 占8个字节  
            raf.writeUTF("UTF严"); // UTF-8格式写入
            raf.writeChar('\n'); // 占2个字符。“换行符”

            raf.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 通过RandomAccessFile读取文件
     */
    private static void testRead()
    {
        try
        {
            // 创建文件“file.txt”对应File对象
            File file = new File(FileName);
            // 创建文件“file.txt”对应的RandomAccessFile对象，以只读方式打开
            RandomAccessFile raf = new RandomAccessFile(file, "r");

            // 读取一个字符
            char c1 = raf.readChar();
            System.out.println("c1=" + c1);
            // 读取一个字符
            char c2 = raf.readChar();
            System.out.println("c2=" + c2);

            // 跳过54个字节。
            raf.seek(54);

            // 测试read(byte[] buffer, int byteOffset, int byteCount)
            byte[] buf = new byte[20];
            raf.read(buf, 0, buf.length);
            System.out.println("buf=" + (new String(buf)));

            raf.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
