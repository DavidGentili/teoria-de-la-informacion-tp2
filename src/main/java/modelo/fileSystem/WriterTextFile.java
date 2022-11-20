package modelo.fileSystem;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WriterTextFile {
    private static final int WRITE_RANGE = 10000;

    private File file;
    private BufferedWriter writer;

    public WriterTextFile(String fileName, boolean append) throws IOException {
        file = new File(fileName);
        writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(file, append), StandardCharsets.UTF_8));

    }

    public WriterTextFile(String fileName) throws IOException {
        file = new File(fileName);
        writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(file), StandardCharsets.UTF_8));
    }

    public void writeString(String str) throws IOException {
            writer.write(str);
    }

    public void close() throws IOException {
        file = null;
        writer.close();
    }
}
