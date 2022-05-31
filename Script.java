import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Script implements IScript {
	
	private Queue<String> scriptLines;
	
	private File file;
	

	@Override
	public String getNextLine() {
		return this.scriptLines.poll();
	}
	
	public Script(ArrayList<String> scriptLines, File file) {
		this.file = file;
		this.scriptLines = new ArrayDeque<String>();
		for (String currentLine : scriptLines) {
			this.scriptLines.add(currentLine);
		}
	}

	
	/**
	 * @Override
	 * Скрипт считается завершённым, если в нём более нет линий
	 */
	public boolean isFinished() {
		return this.scriptLines.isEmpty();
	}


	@Override
	public File getFile() {
		return this.file;
	}


	public Queue<String> getScriptLines() {
		return scriptLines;
	}


	public void setScriptLines(Queue<String> scriptLines) {
		this.scriptLines = scriptLines;
	}


	public void setFile(File file) {
		this.file = file;
	}
	
	

}
