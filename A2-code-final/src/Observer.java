import java.io.IOException;

public interface Observer {

    void init() throws IOException;

    void run() throws IOException;

    void end();

}