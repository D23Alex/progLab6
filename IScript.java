import java.io.File;

public interface IScript {

	public String getNextLine();

	public boolean isFinished();
	
	public File getFile();

}
