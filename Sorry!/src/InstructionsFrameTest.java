import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author sturgedl.
 *         Created Apr 18, 2013.
 */
public class InstructionsFrameTest {

	@Test
	public void test() {
		assertTrue(true);
	}
	
	
	@Test
	public void testObtainsCorrectFileName(){
		String lang = "english";
		String check = InstructionsFrame.obtainFileName(lang);
		
		assertEquals("/images/instructions_" + lang + ".html", check);
		
		lang = "french";
		check = InstructionsFrame.obtainFileName(lang);
		assertEquals("/images/instructions_" + lang + ".html", check);
	}
	
	@Test
	public void testObtainJEditorPane(){
		
		JEditorPane target = null;
		try {
			target = InstructionsFrame.createEditorPane("/images/instructions_english.html");
		} catch (IOException exception) {
		}
		assertNotNull(target);
		
		assertEquals(target.getContentType(), "text/html");
	}
	
	@Test
	public void testCreateScrollPane(){
		JScrollPane target = InstructionsFrame.createScrollPane(new JEditorPane());
		assertNotNull(target);
	}

}
