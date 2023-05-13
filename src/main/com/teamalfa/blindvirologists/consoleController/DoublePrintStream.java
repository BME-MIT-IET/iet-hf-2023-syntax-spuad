package main.com.teamalfa.blindvirologists.consoleController;

import java.io.*;

public class DoublePrintStream extends PrintStream {
    private final OutputStream fos;

    DoublePrintStream(OutputStream out, String filename){
        super(out);

        try {
            fos = new FileOutputStream(new File(filename));
        } catch (FileNotFoundException e) {
            throw new AssertionError("cant create file", e);
        }
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);

        try {
            fos.write(buf, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            super.close();
        }
    }
}
