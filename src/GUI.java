import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    JFrame notepadWindow, findAndReplaceWindow;
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu menuFile, menuEdit;
    JMenuItem fNew, fOpen, fSave, fSaveAs, fExit, eFindAndReplace, eUndo, eRedo;
    UndoManager undoManager = new UndoManager();

    FileMenu file = new FileMenu(this);
    EditMenu edit = new EditMenu(this);

    HotkeysHandler hotkeysHandler = new HotkeysHandler(this);

    public GUI() {
        createNotepadWindow();
        createTextArea();
        createMenuBar();
        createFileMenu();
        createEditMenu();

        notepadWindow.setVisible(true);
    }

    public GUI(String data) {
        this();
        textArea.setText(data);
    }

    public void createNotepadWindow() {
        notepadWindow = new JFrame("Notepad");

        ImageIcon img = new ImageIcon("icon.png");
        notepadWindow.setIconImage(img.getImage());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        notepadWindow.setBounds(
                (int)(0.2 * dimension.width),
                (int)(0.2 * dimension.height),
                600,
                350);

        notepadWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createFindAndReplaceWindow() {
        findAndReplaceWindow = new JFrame("Find and Replace");
        findAndReplaceWindow.setLayout(null);
        findAndReplaceWindow.setResizable(false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        findAndReplaceWindow.setBounds(
                dimension.width / 2 - 166,
                dimension.height / 2 - 112,
                333,
                225);
    }

    public void createTextArea() {
        textArea = new JTextArea();

        textArea.addKeyListener(hotkeysHandler);

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });

        scrollPane = new JScrollPane(
                textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        notepadWindow.add(scrollPane);
    }

    public void createMenuBar() {
        menuBar = new JMenuBar();
        notepadWindow.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);
    }

    public void createFileMenu() {
        fNew = new JMenuItem("New");
        fNew.addActionListener(this);
        fNew.setActionCommand("New");
        menuFile.add(fNew);

        fOpen = new JMenuItem("Open");
        fOpen.addActionListener(this);
        fOpen.setActionCommand("Open");
        menuFile.add(fOpen);

        fSave = new JMenuItem("Save");
        fSave.addActionListener(this);
        fSave.setActionCommand("Save");
        menuFile.add(fSave);

        fSaveAs = new JMenuItem("Save As");
        fSaveAs.addActionListener(this);
        fSaveAs.setActionCommand("Save as");
        menuFile.add(fSaveAs);

        fExit = new JMenuItem("Exit");
        fExit.addActionListener(this);
        fExit.setActionCommand("Exit");
        menuFile.add(fExit);
    }

    public void createEditMenu() {
        eFindAndReplace = new JMenuItem("Find and Replace");
        eFindAndReplace.addActionListener(this);
        eFindAndReplace.setActionCommand("Find and Replace");
        menuEdit.add(eFindAndReplace);

        eUndo = new JMenuItem("Undo");
        eUndo.addActionListener(this);
        eUndo.setActionCommand("Undo");
        menuEdit.add(eUndo);

        eRedo = new JMenuItem("Redo");
        eRedo.addActionListener(this);
        eRedo.setActionCommand("Redo");
        menuEdit.add(eRedo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New" -> file.newFile();
            case "Open" -> file.open();
            case "Save" -> file.save();
            case "Save as" -> file.saveAs();
            case "Exit" -> file.exit();
            case "Find and Replace" -> edit.findAndReplace();
            case "Undo" -> edit.undo();
            case "Redo" -> edit.redo();
        }
    }
}
