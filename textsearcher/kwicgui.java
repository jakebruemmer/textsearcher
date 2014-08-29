import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import kwic.KWIC;
import kwic.Phrase;
import kwic.Word;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;
/** 
 * Key Word in Context
 * 
 * Jake Bruemmer (Student ID: 419767)
 * Lab Section B
 * jakebruemmer@wustl.edu
 * CSE 132 Lab 2b
 * KWICGUI.java
 * Searches through given file or String array to find the number of Phrases that 
 * contain a given Word. The Word and Phrase objects are defined in other classes. 
 * KWIC class has PropertyChangeSupport and HashMap instance variables. Words are 
 * used as key values mapped to a Set of Phrases that contain that Word.
 *
 */

public class KWICGUI extends JFrame {

	private JPanel contentPane;
	private KWIC kwic;
	private PropertyChangeSupport pcs;
	private JTextField wordSearch;
	private DefaultListModel listModel;
	private JTable table;
	private DefaultHighlightPainter highlightPainter;
	private Set<Word> deletedWords;
	private Set<Phrase> deletedPhrases;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KWICGUI frame = new KWICGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public KWICGUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 500, 850, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		kwic = new KWIC();
		pcs = kwic.getPCS();
		kwic.addPhrases(new File("fortunes.txt"));
		deletedWords = new HashSet<Word>();
		deletedPhrases = new HashSet<Phrase>();

		final JLabel queryLabel = new JLabel("");
		queryLabel.setBounds(432, 13, 388, 22);
		contentPane.add(queryLabel);

		final JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 13, 408, 98);
		textArea.setLineWrap(true);
		contentPane.add(textArea);

		JScrollPane scrollPaneMain = new JScrollPane(textArea);
		scrollPaneMain.setBounds(12, 13, 408, 98);
		contentPane.add(scrollPaneMain);

		final JTextArea resultsArea = new JTextArea();
		resultsArea.setBounds(443, 124, 377, 101);
		contentPane.add(resultsArea);

		JScrollPane scrollPaneResults = new JScrollPane(resultsArea);
		scrollPaneResults.setBounds(443, 124, 377, 101);
		contentPane.add(scrollPaneResults);

		wordSearch = new JTextField();
		wordSearch.setBounds(493, 40, 165, 22);
		contentPane.add(wordSearch);
		wordSearch.setColumns(10);

		JButton btnGo = new JButton("Go!");
		btnGo.setBounds(666, 39, 97, 25);
		contentPane.add(btnGo);

		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setBounds(442, 43, 56, 16);
		contentPane.add(lblSearch);

		final JTextArea resultsInfo = new JTextArea();
		resultsInfo.setBounds(443, 75, 377, 36);
		resultsInfo.setLineWrap(true);
		contentPane.add(resultsInfo);

		BufferedReader in = new BufferedReader(new FileReader("fortunes.txt"));
		String line = in.readLine();
		while(line != null) {
			textArea.append(line + "\n");
			line = in.readLine();
		}

		final JList wordList = new JList<Word>();
		Object[] data = kwic.getWords().toArray();
		wordList.setListData(data);
		wordList.setBounds(12, 141, 133, 100);
		contentPane.add(wordList);

		JScrollPane listScroller = new JScrollPane(wordList);
		listScroller.setBounds(12, 141, 133, 100);
		contentPane.add(listScroller);

		final JList phraseList = new JList<Phrase>();
		Set<Phrase> phrases = new HashSet<Phrase>();
		for(Word w : kwic.getWords()) {
			for(Phrase p : kwic.getPhrases(w)) {
				phrases.add(p);
			}
		}
		Object[] phraseData = phrases.toArray();
		phraseList.setListData(phraseData);
		phraseList.setBounds(12, 301, 408, 138);
		contentPane.add(phraseList);

		JScrollPane phraseListScroll = new JScrollPane(phraseList);
		phraseListScroll.setBounds(12, 301, 408, 138);
		contentPane.add(phraseListScroll);

		JLabel lblAListOf = new JLabel("A list of the words:");
		lblAListOf.setBounds(10, 112, 135, 16);
		contentPane.add(lblAListOf);

		JButton btnDeleteWord = new JButton("Delete Word");
		btnDeleteWord.setBounds(22, 247, 109, 25);
		contentPane.add(btnDeleteWord);
		
		JLabel lblAListOf_1 = new JLabel("A list of the phrases:");
		lblAListOf_1.setBounds(12, 285, 272, 16);
		contentPane.add(lblAListOf_1);
		
		JButton btnDeletePhrase = new JButton("Delete Phrase");
		btnDeletePhrase.setBounds(296, 267, 124, 25);
		contentPane.add(btnDeletePhrase);
		
		JButton btnForceAssociation = new JButton("Force Association");
		btnForceAssociation.setBounds(149, 139, 135, 25);
		contentPane.add(btnForceAssociation);
		
		JButton btnDropAssociation = new JButton("Drop Association");
		btnDropAssociation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDropAssociation.setBounds(149, 168, 135, 25);
		contentPane.add(btnDropAssociation);
		
		goBtnActionListener(btnGo, textArea, resultsArea, resultsInfo, queryLabel, wordList);

		wordListKeyListener(wordList, textArea, resultsArea, queryLabel, resultsInfo);
		
		final JList addWordList = new JList();
		addWordList.setBounds(296, 142, 135, 101);
		addWordList.setListData(deletedWords.toArray());
		contentPane.add(addWordList);
		
		JScrollPane dws = new JScrollPane(addWordList);
		dws.setBounds(296, 142, 135, 101);
		contentPane.add(dws);
		
		final JList addPhraseList = new JList();
		addPhraseList.setBounds(443, 302, 377, 137);
		addPhraseList.setListData(deletedPhrases.toArray());
		contentPane.add(addPhraseList);
		
		JScrollPane dps = new JScrollPane(addPhraseList);
		dps.setBounds(443, 302, 377, 137);
		contentPane.add(dps);
		
		JLabel lblDeletedWords = new JLabel("Deleted Words:");
		lblDeletedWords.setBounds(296, 120, 97, 16);
		contentPane.add(lblDeletedWords);
		
		JLabel lblDeletedPhrases = new JLabel("Deleted Phrases:");
		lblDeletedPhrases.setBounds(442, 271, 109, 16);
		contentPane.add(lblDeletedPhrases);
		
		JButton btnAddPhrase = new JButton("Add Phrase");
		btnAddPhrase.setBounds(685, 267, 135, 25);
		contentPane.add(btnAddPhrase);
		
		btnAddPhrase.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						kwic.addPhrase((Phrase) addPhraseList.getSelectedValue());
						Set<Phrase> phrases = new HashSet<Phrase>();
						for(Word w : kwic.getWords()) {
							for(Phrase p : kwic.getPhrases(w)) {
								phrases.add(p);
							}
						}
						Object[] phraseData = phrases.toArray();
						phraseList.setListData(phraseData);
						queryLabel.setText("Added " + ((Phrase) addPhraseList.getSelectedValue()).toString() +
								" to phrase list.");
						deletedPhrases.remove((Phrase) addPhraseList.getSelectedValue());
						addPhraseList.setListData(deletedPhrases.toArray());
						
					}
				});
		
		addPhraseList.addKeyListener(
				new KeyListener() {
					public void keyTyped(KeyEvent e) {
					}
					public void keyPressed(KeyEvent e) {
					}
					public void keyReleased(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							kwic.addPhrase((Phrase) addPhraseList.getSelectedValue());
							Set<Phrase> phrases = new HashSet<Phrase>();
							for(Word w : kwic.getWords()) {
								for(Phrase p : kwic.getPhrases(w)) {
									phrases.add(p);
								}
							}
							Object[] phraseData = phrases.toArray();
							phraseList.setListData(phraseData);
							queryLabel.setText("Added " + ((Phrase) addPhraseList.getSelectedValue()).toString() +
									" to phrase list.");
							deletedPhrases.remove((Phrase) addPhraseList.getSelectedValue());
							addPhraseList.setListData(deletedPhrases.toArray());
						}
					}
				});
		
		
		btnDeleteWord.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						deletedWords.add((Word) wordList.getSelectedValue());
						kwic.deleteWord((Word) wordList.getSelectedValue());
						queryLabel.setText("Deleted " + ((Word) wordList.getSelectedValue()).toString() + " from the word list.");
						Object[] data = kwic.getWords().toArray();
						wordList.setListData(data);
						addWordList.setListData(deletedWords.toArray());
						// need pcs notifier
					}
				});
		
		wordList.addKeyListener(
				new KeyListener() {
					public void keyTyped(KeyEvent e) {
					}
					public void keyPressed(KeyEvent e) {
					}
					public void keyReleased(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_DELETE) {
							queryLabel.setText("Deleted " + ((Word) wordList.getSelectedValue()).toString() + " from the word list.");
							deletedWords.add((Word) wordList.getSelectedValue());
							kwic.deleteWord((Word) wordList.getSelectedValue());
							Object[] data = kwic.getWords().toArray();
							wordList.setListData(data);
							addWordList.setListData(deletedWords.toArray());
						}
					}
				});
		
		btnDeletePhrase.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						deletedPhrases.add((Phrase) phraseList.getSelectedValue());
						kwic.deletePhrase((Phrase) phraseList.getSelectedValue());
						queryLabel.setText("Deleted " + ((Phrase) phraseList.getSelectedValue()).toString() + " from the phrase list.");
						Set<Phrase> phrases = new HashSet<Phrase>();
						for(Word w : kwic.getWords()) {
							for(Phrase p : kwic.getPhrases(w)) {
								phrases.add(p);
							}
						}
						Object[] phraseData = phrases.toArray();
						phraseList.setListData(phraseData);
						addPhraseList.setListData(deletedPhrases.toArray());
						// need pcs notifier
					}
				});
		
		btnDropAssociation.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						kwic.dropAssoc((Word) wordList.getSelectedValue(), (Phrase) phraseList.getSelectedValue());
						// need pcs notifier
					}
				});
		
		btnForceAssociation.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						kwic.forceAssoc((Word) wordList.getSelectedValue(), (Phrase) phraseList.getSelectedValue());
						// need pcs notifier
					}
				});
		
		phraseList.addKeyListener(
				new KeyListener() {
					public void keyTyped(KeyEvent e){
					}
					public void keyPressed(KeyEvent e) {
					}
					public void keyReleased(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_DELETE) {
							kwic.deletePhrase((Phrase) phraseList.getSelectedValue());
							deletedPhrases.add((Phrase) phraseList.getSelectedValue());
							queryLabel.setText("Deleted " + ((Phrase) phraseList.getSelectedValue()).toString() + " from the phrase list.");
							Set<Phrase> phrases = new HashSet<Phrase>();
							for(Word w : kwic.getWords()) {
								for(Phrase p : kwic.getPhrases(w)) {
									phrases.add(p);
								}
							}
							Object[] phraseData = phrases.toArray();
							phraseList.setListData(phraseData);
							addPhraseList.setListData(deletedPhrases.toArray());
						}
					}
				});

		phraseList.addKeyListener(
				new KeyListener() {
					public void keyTyped(KeyEvent e) {
					}
					public void keyPressed(KeyEvent e) {
					}
					public void keyReleased(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							textArea.getHighlighter().removeAllHighlights();
							resultsArea.setText("");
							resultsInfo.setText("Here's a list of the words in that phrase: ");
							queryLabel.setText("Look below for the words in this phrase!");
							Set<Word> word = ((Phrase) phraseList.getSelectedValue()).getWords();
							for(Word w : word) {
								resultsArea.append(w.toString() + "\n");
							}
							queryLabel.setText("");
							Document document = textArea.getDocument();
							try {
								Phrase find = new Phrase(((Phrase) phraseList.getSelectedValue()).getString());
								int[] indexArray = new int[word.size()];
								int n = 0;
								for (int index = 0; index + find.getString().length() < document.getLength(); index++) {
									Phrase match = new Phrase(document.getText(index, find.getString().length()));
									if (find.equals(match)) {
										indexArray[n] = index;
										highlightPainter = new DefaultHighlightPainter(Color.YELLOW);
										textArea.getHighlighter().addHighlight(index, index + find.getString().length(),
												highlightPainter);
										++n;
									}
								}
								Rectangle viewRect = textArea.modelToView(indexArray[0]);
								textArea.scrollRectToVisible(viewRect);
								textArea.setCaretPosition(indexArray[0] + find.getString().length());
								textArea.moveCaretPosition(indexArray[0]);
							} catch (BadLocationException ex) {
								ex.printStackTrace();
							}
						}

					}
				});

	}
	
	
	/**
	 * Adds an action listener to the Go JButton that searches the file for the supplied word
	 * @param goBtn
	 * @param mainText
	 * @param results
	 * @param info
	 * @param label
	 * @param list
	 */
	public void goBtnActionListener(JButton goBtn, final JTextArea mainText, final JTextArea results, final JTextArea info, final JLabel label, 
			final JList list) {
		goBtn.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainText.getHighlighter().removeAllHighlights();
						results.setText("");
						Set<Phrase> search = kwic.getPhrases(new Word(wordSearch.getText()));
						for(Phrase p : search) {
							results.append(p.toString() + "\n");
						}
						label.setText("You searched: " + wordSearch.getText() + 
								". There are " + search.size() + " phrases that contain"
								+ " that word!");
						info.setText("The area below contains the phrases with the word that you searched.");
						Document document = mainText.getDocument();
						try {
							Word find = new Word(wordSearch.getText());
							for (int index = 0; index + find.getOriginalWord().length() < document.getLength(); index++) {
								Word match = new Word(document.getText(index, find.getOriginalWord().length()));
								if (find.equals(match)) {
									highlightPainter = new DefaultHighlightPainter(Color.YELLOW);
									mainText.getHighlighter().addHighlight(index, index + find.getOriginalWord().length(),
											highlightPainter);
									Rectangle viewRect = mainText.modelToView(index);
									mainText.scrollRectToVisible(viewRect);
									mainText.setCaretPosition(index + find.getOriginalWord().length());
									mainText.moveCaretPosition(index);
								}
							}
							// go to first appearance of word in text
						} catch (BadLocationException ex) {
							ex.printStackTrace();
						}
						list.setSelectedValue(new Word(wordSearch.getText()), true);
					}

				});
		
	}
	
	
	/**
	 * Adds an key listener to the word list for the Enter Key
	 * @param wordList
	 * @param textArea
	 * @param resultsArea
	 * @param queryLabel
	 * @param resultsInfo
	 */
	public void wordListKeyListener(final JList wordList, final JTextArea textArea, final JTextArea resultsArea, final JLabel queryLabel,
			final JTextArea resultsInfo) {
		wordList.addKeyListener(
				new KeyListener() {
					public void keyTyped(KeyEvent e) {
					}
					public void keyPressed(KeyEvent e) {
					}
					public void keyReleased(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							textArea.getHighlighter().removeAllHighlights();
							resultsArea.setText("");
							wordSearch.setText(((Word) wordList.getSelectedValue()).toString());
							Set<Phrase> phrases = kwic.getPhrases((Word) wordList.getSelectedValue());
							for(Phrase p : phrases) {
								resultsArea.append(p.toString() + "\n");
							}
							queryLabel.setText("You searched: " + wordList.getSelectedValue() + 
									". There are " + phrases.size() + " phrases that contain"
									+ " that word!");
							resultsInfo.setText("The area below contains the phrases with the word that you searched.");
							Document document = textArea.getDocument();
							try {
								Word find = new Word(((Word) wordList.getSelectedValue()).getOriginalWord());
								for (int index = 0; index + find.getOriginalWord().length() < document.getLength(); index++) {
									Word match = new Word(document.getText(index, find.getOriginalWord().length()));
									if (find.equals(match)) {
										highlightPainter = new DefaultHighlightPainter(Color.YELLOW);
										textArea.getHighlighter().addHighlight(index, index + find.getOriginalWord().length(),
												highlightPainter);
										Rectangle viewRect = textArea.modelToView(index);
										textArea.scrollRectToVisible(viewRect);
										textArea.setCaretPosition(index + find.getOriginalWord().length());
										textArea.moveCaretPosition(index);
									}
								}
								// go to first appearance of word in text
							} catch (BadLocationException ex) {
								ex.printStackTrace();
							}
						}
					}
				});
	}
}
