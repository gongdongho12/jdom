package org.jdom2.test.cases;

/* Please run replic.pl on me ! */
/**
 * Please put a description of your test here.
 * 
 * @author unascribed
 * @version 0.1
 */
import org.jdom2.*;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.jdom2.output.*;
import org.jdom2.test.util.UnitTestUtil;

public final class TestDocument {

	/**
	 *  a directory for temporary storage of files
	 */
	private String scratchDir = ResourceBundle.getBundle("org.jdom2.test.Test")
			.getString("test.scratchDirectory");

	/**
	 * The main method runs all the tests in the text ui
	 */
	public static void main (String args[]) 
	{
		JUnitCore.runClasses(TestDocument.class);
	}

	/**
	 * Test constructor of Document with a List of content including the root element.
	 */
	@Test
	public void test_TCC___List() {
		Element bogus = new Element("bogus-root");
		Element element = new Element("element");
		Comment comment = new Comment("comment");
		List list = new ArrayList();

		list.add(element);
		list.add(comment);
		Document doc = new Document(list);
		// Get a live list back
		list = doc.getContent();
		assertEquals("incorrect root element returned", element, doc.getRootElement());

		//no root element
		element.detach();
		try {
			doc.getRootElement();
			fail("didn't catch missing root element");
		} catch (IllegalStateException e) {
		}

		//set root back, then try to add another element to our
		//live list
		doc.setRootElement(element);
		try {
			list.add(bogus);
			fail("didn't catch duplicate root element");
		} catch (IllegalAddException e) {
		}
		assertEquals("incorrect root element returned", element, doc.getRootElement());

		//how about replacing it in our live list
		try {
			Element oldRoot = doc.getRootElement();
			int i = doc.indexOf(oldRoot);
			list.set(i, bogus);
		} catch (Exception e) {
			fail("Root replacement shouldn't have throw a exception");
		}
		//and through the document
		try {
			doc.setRootElement(element);
		} catch (Exception e) {
			fail("Root replacement shouldn't have throw a exception");
		}

		list = null;
		try {
			doc = new Document(list);
		} catch (IllegalAddException e) {
			fail("didn't handle null list");
		} catch (NullPointerException e) {
			fail("didn't handle null list");
		}

	}

	/**
	 * Test constructor of a Document with a List of content and a DocType.
	 */
	@Test
	public void test_TCC___List_OrgJdomDocType() {
		Element bogus = new Element("bogus-root");
		Element element = new Element("element");
		Comment comment = new Comment("comment");
		DocType docType = new DocType("element");
		List list = new ArrayList();

		list.add(docType);
		list.add(element);
		list.add(comment);
		Document doc = new Document(list);
		// Get a live list back
		list = doc.getContent();
		assertEquals("incorrect root element returned", element, doc.getRootElement());
		assertEquals("incorrect doc type returned", docType, doc.getDocType());

		element.detach();
		try {
			doc.getRootElement();
			fail("didn't catch missing root element");
		} catch (IllegalStateException e) {
		}

		//set root back, then try to add another element to our
		//live list
		doc.setRootElement(element);
		try {
			list.add(bogus);
			fail("didn't catch duplicate root element");
		} catch (IllegalAddException e) {
		}
		assertEquals("incorrect root element returned", element, doc.getRootElement());

		//how about replacing it in our live list
		try {
			Element oldRoot = doc.getRootElement();
			int i = doc.indexOf(oldRoot);
			list.set(i,bogus);
		} catch (Exception e) {
			fail("Root replacement shouldn't have throw a exception");
		}
		//and through the document
		try {
			doc.setRootElement(element);
		} catch (Exception e) {
			fail("Root replacement shouldn't have throw a exception");
		}

		list = null;
		try {
			doc = new Document(list);
		} catch (IllegalAddException e) {
			fail("didn't handle null list");
		} catch (NullPointerException e) {
			fail("didn't handle null list");
		}

	}

	/**
	 * Test the constructor with only a root element.
	 */
	@Test
	public void test_TCC___OrgJdomElement() {
		Element element = new Element("element");

		Document doc = new Document(element);
		assertEquals("incorrect root element returned", element, doc.getRootElement());

		element = null;
		try {
			doc = new Document(element);
		} catch (IllegalAddException e) {
			fail("didn't handle null element");
		} catch (NullPointerException e) {
			fail("didn't handle null element");
		}

	}

	/**
	 * Test constructor of Document with and Element and Doctype
	 */
	@Test
	public void test_TCC___OrgJdomElement_OrgJdomDocType() {
		Element element = new Element("element");
		DocType docType = new DocType("element");

		Document doc = new Document(element, docType);
		assertEquals("incorrect root element returned", element, doc.getRootElement());
		assertEquals("incorrect doc type returned", docType, doc.getDocType());

		docType = new DocType("element");
		element = null;
		try {
			doc = new Document(element, docType);
		} catch (IllegalAddException e) {
			fail("didn't handle null element");
		} catch (NullPointerException e) {
			fail("didn't handle null element");
		}

	}

	/**
	 * Test object equality.
	 */
	@Test
	public void test_TCM__boolean_equals_Object() {
		Element element = new Element("element");

		Document doc = new Document(element);
		assertEquals("invalid object equality", doc, (Object)doc);

	}

	/**
	 * Test removeContent for a given Comment
	 */
	@Test
	public void test_TCM__boolean_removeContent_OrgJdomComment() {
		Element element = new Element("element");
		Comment comment = new Comment("comment");
		ArrayList list = new ArrayList();

		list.add(element);
		list.add(comment);
		Document doc = new Document(list);
		assertTrue("incorrect comment removed",! doc.removeContent(new Comment("hi")));
		assertTrue("didn't remove comment", doc.removeContent(comment));

		assertTrue("comment not removed", doc.getContent().size() == 1);
	}

	/**
	 * Test removeContent with the supplied ProcessingInstruction.
	 */
	@Test
	public void test_TCM__boolean_removeContent_OrgJdomProcessingInstruction() {
		Element element = new Element("element");
		ProcessingInstruction pi = new ProcessingInstruction("test", "comment");
		ArrayList list = new ArrayList();

		list.add(element);
		list.add(pi);
		Document doc = new Document(list);
		assertTrue("incorrect pi removed",! doc.removeContent(new ProcessingInstruction("hi", "there")));
		assertTrue("didn't remove pi", doc.removeContent(pi));

		assertTrue("PI not removed", doc.getContent().size() == 1);

	}

	/**
	 * Test hashcode function.
	 */
	@Test
	public void test_TCM__int_hashCode() {
		Element element = new Element("test");
		Document doc = new Document(element);

		//only an exception would be a problem
		int i = -1;
		try {
			i = doc.hashCode();
		}
		catch(Exception e) {
			fail("bad hashCode");
		}


		Element element2 = new Element("test");
		Document doc2 = new Document(element2);
		//different Documents, same text
		int x = doc2.hashCode();
		assertTrue("Different Elements with same value have same hashcode", x != i);

	}

	/**
	 * Test code goes here. Replace this comment.
	 */
	@Test
	public void test_TCM__Object_clone() {
		//do the content tests to 2 levels deep to verify recursion
		Element element = new Element("el");
		Namespace ns = Namespace.getNamespace("urn:hogwarts");
		element.setAttribute(new Attribute("name", "anElement"));
		Element child1 = new Element("child", ns);
		child1.setAttribute(new Attribute("name", "first"));

		Element child2 = new Element("firstChild", ns);
		child2.setAttribute(new Attribute("name", "second"));
		Element child3 = new Element("child", Namespace.getNamespace("ftp://wombat.stew"));
		child1.addContent(child2);
		element.addContent(child1);
		element.addContent(child3);

		//add mixed content to the nested child2 element
		Comment comment = new Comment("hi");
		child2.addContent(comment);
		CDATA cdata = new CDATA("gotcha");
		child2.addContent(cdata);
		ProcessingInstruction pi = new ProcessingInstruction("tester", "do=something");
		child2.addContent(pi);
		EntityRef entity = new EntityRef("wizards");
		child2.addContent(entity);
		child2.addContent("finally a new wand!");

		//a little more for the element
		element.addContent("top level element text");
		Comment topComment = new Comment("some comment");

		Document doc = new Document(element);
		doc.addContent(topComment);
		Document docClone = (Document)doc.clone();
		element = null;
		child3 = null;
		child2 = null;
		child1 = null;

		List list = docClone.getRootElement().getContent();

		//finally the test
		assertEquals("wrong comment", ((Comment)docClone.getContent().get(1)).getText(), "some comment");
		assertEquals("wrong child element", ((Element)list.get(0)).getName(), "child" );
		assertEquals("wrong child element", ((Element)list.get(1)).getName(), "child" );
		Element deepClone = ((Element)list.get(0)).getChild("firstChild", Namespace.getNamespace("urn:hogwarts"));

		assertEquals("wrong nested element","firstChild", deepClone.getName());
		//comment
		assertTrue("deep clone comment not a clone", deepClone.getContent().get(0) != comment);
		comment = null;
		assertEquals("incorrect deep clone comment", "hi", ((Comment)deepClone.getContent().get(0)).getText());
		//CDATA

		assertEquals("incorrect deep clone CDATA", "gotcha", ((CDATA)deepClone.getContent().get(1)).getText());
		//PI
		assertTrue("deep clone PI not a clone", deepClone.getContent().get(2) != pi);
		pi = null;
		assertEquals("incorrect deep clone PI", "do=something",((ProcessingInstruction)deepClone.getContent().get(2)).getData());
		//entity
		assertTrue("deep clone Entity not a clone", deepClone.getContent().get(3) != entity);
		entity = null;
		assertEquals("incorrect deep clone Entity", "wizards", ((EntityRef)deepClone.getContent().get(3)).getName());
		//text
		assertEquals("incorrect deep clone test", "finally a new wand!", ((Text)deepClone.getContent().get(4)).getText());


	}

	/**
	 * Test getDocType.
	 */
	@Test
	public void test_TCM__OrgJdomDocType_getDocType() {
		Element element = new Element("element");
		DocType docType = new DocType("element");

		Document doc = new Document(element, docType);
		assertEquals("incorrect root element returned", element, doc.getRootElement());
		assertEquals("incorrect doc type returned", docType, doc.getDocType());

	}

	/**
	 * Test the addition of comments to Documents.
	 */
	@Test
	public void test_TCM__OrgJdomDocument_addContent_OrgJdomComment() {
		Element element = new Element("element");
		Comment comment = new Comment("comment");
		Comment comment2 = new Comment("comment 2");

		Document doc = new Document(element);
		doc.addContent(comment);
		doc.addContent(comment2);
		List content = doc.getContent();

		assertEquals("wrong number of comments in List", 3, content.size());
		assertEquals("wrong comment", comment, content.get(1));
		assertEquals("wrong comment", comment2, content.get(2));
	}

	/**
	 * Test the addition of ProcessingInstructions to Documents.
	 */
	@Test
	public void test_TCM__OrgJdomDocument_addContent_OrgJdomProcessingInstruction() {
		Element element = new Element("element");
		ProcessingInstruction pi = new ProcessingInstruction("test", "comment");
		ProcessingInstruction pi2 = new ProcessingInstruction("test", "comment 2");

		Document doc = new Document(element);
		doc.addContent(pi);
		doc.addContent(pi2);
		List content = doc.getContent();

		assertEquals("wrong number of PI's in List", 3, content.size());
		assertEquals("wrong PI", pi, content.get(1));
		assertEquals("wrong PI", pi2, content.get(2));
	}

	/**
	 * Test that setRootElement works as expected.
	 */
	@Test
	public void test_TCM__OrgJdomDocument_setRootElement_OrgJdomElement() {
		Element element = new Element("element");

		Document doc1 = new Document(element);
		assertEquals("incorrect root element returned", element, doc1.getRootElement());
		Document doc2 = new Document();
		try {
			doc2.setRootElement(element);
			fail("didn't catch element already attached to anohter document");
		}
		catch(IllegalAddException e) {
		}
	}

	/**
	 * Test that setDocType works as expected.
	 */
	@Test
	public void test_TCM__OrgJdomDocument_setDocType_OrgJdomDocType() {
		Element element = new Element("element");
		DocType docType = new DocType("element");

		Document doc = new Document(element);
		doc.setDocType(docType);
		assertEquals("incorrect root element returned", element, doc.getRootElement());
		assertEquals("incorrect doc type returned", docType, doc.getDocType());
	}

	/**
	 * Test that a Document can return a root element.
	 */
	@Test
	public void test_TCM__OrgJdomElement_getRootElement() {
		Element element = new Element("element");

		Document doc = new Document(element);
		assertEquals("incorrect root element returned", element, doc.getRootElement());
	}

	/**
	 * Test that the toString method returns the expected result.
	 */
	@Test
	public void test_TCM__String_toString() {
		Element element = new Element("element");
		DocType docType = new DocType("element");

		Document doc = new Document(element, docType);
		String buf = new String("[Document: [DocType: <!DOCTYPE element>], Root is [Element: <element/>]]");
		assertEquals("incorrect root element returned", buf, doc.toString());

	}

	/**
	 * Test that an Element properly handles default namespaces
	 */
    @Test
	public void test_TCU__testSerialization() throws IOException, ClassNotFoundException {

		//set up an element to test with
		Element element= new Element("element", Namespace.getNamespace("http://foo"));
		Element child1 = new Element("child1");
		Element child2 = new Element("child2");

		element.addContent(child1);
		element.addContent(child2);

		Document doc = new Document(element);



		//here is what we expect in these two scenarios
		//String bufWithNoNS = "<element xmlns=\"http://foo\"><child1 /><child2 /></element>";

		String bufWithEmptyNS = "<element xmlns=\"http://foo\"><child1 xmlns=\"\" /><child2 xmlns=\"\" /></element>";

		Document docIn = UnitTestUtil.deSerialize(doc);
		element = docIn.getRootElement();

		StringWriter sw = new StringWriter();
		XMLOutputter op= new XMLOutputter(Format.getRawFormat());
		op.output(element, sw);
		assertTrue("Incorrect data after serialization", sw.toString().equals(bufWithEmptyNS));

	}

	/**
	 * Test getContent
	 */
    @Test
	public void test_TCM__List_getContent() {
		Element element = new Element("element");
		Comment comment = new Comment("comment");
		ArrayList list = new ArrayList();

		list.add(element);
		list.add(comment);
		Document doc = new Document(list);
		assertEquals("missing mixed content", list, doc.getContent());
		assertEquals("wrong number of elements", 2, doc.getContent().size());
	}

	/**
	 * Test that setContent works according to specs.
	 */
    @Test
	public void test_TCM__OrgJdomDocument_setContent_List() {
		Element element = new Element("element");
		Element newElement = new Element("newEl");
		Comment comment = new Comment("comment");
		ProcessingInstruction pi = new ProcessingInstruction("foo", "bar");
		ArrayList list = new ArrayList();

		list.add(newElement);
		list.add(comment);
		list.add(pi);


		Document doc = new Document(element);
		doc.setContent(list);
		assertEquals("wrong number of elements", 3, doc.getContent().size());
		assertEquals("missing element", newElement, doc.getContent().get(0));
		assertEquals("missing comment", comment, doc.getContent().get(1));
		assertEquals("missing pi", pi, doc.getContent().get(2));
	}
    
	@Test
	public void testDocumentAddDocType() {
		try {
			Document doc = new Document();
			doc.addContent(new Element("tag"));
			List<Content> list = (List<Content>)doc.getContent();
			list.add(new DocType("elementname"));
			fail ("Should not be able to add DocType to a document after an Element");
		} catch (IllegalAddException iae) {
			// good!
		} catch (Exception e) {
			fail ("We expect an IllegalAddException, but got " + e.getClass().getName());
		}
	}


}
