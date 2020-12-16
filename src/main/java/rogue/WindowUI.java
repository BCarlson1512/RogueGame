package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Random;

public class WindowUI extends JFrame {

    private SwingTerminal terminal;
    private TerminalScreen screen;
    private JTextField dataEntry;
    private JButton enterCommand;
    private static JLabel nameLabel;
    private static JTextArea inventory;
    private static JTextArea commandHistory;
    private static JTextArea equippedItems;
    private Container contentPane;

    public static final int WIDTH = 850;
    public static final int HEIGHT = 500;
    private static final int MAXG = 255;
    private static final int GRIDROWS = 3;
    private static final int GRIDCOLS = 1;
    private static final int EFRAME_HEIGHT = 100;
    private static final int EFRAME_WIDTH = 200;
    private static final int IH8_CHECKSTYLE = 255;
    private static final int INVHEIGHT = 300;
    private static final int INVWIDTH = 300;
    public static final int COLS = 80;
    public static final int ROWS = 24;
    private static final int CONSTANT1 = 25;
    private final char startCol = 0;
    private final char msgRow = 1;
    private final char roomRow = 3;
    private static boolean raveMode = false;
    private static char userInput;
    private static Rogue theGame;
    /**
     * Constructor.
    **/
    public WindowUI() {
        super("my awesome game");
        contentPane = getContentPane();
        setWindowDefaults(getContentPane());
        makeMenuBar(getContentPane());
        setUpPanels();
        pack();
        start();
    }
    private void setUpPanels() {
        JPanel labelPanel = new JPanel();
        JPanel lhsPanel = new JPanel();
        JPanel commandsPanel = new JPanel();
        JPanel inventoryPanel = new JPanel();
        JPanel equippedPanel = new JPanel();
        setupEquippedPanel(equippedPanel);
        setupInventoryPanel(inventoryPanel);
        setupCommandHistory(commandsPanel);
        setupLHS(lhsPanel, inventoryPanel, commandsPanel, equippedPanel);
        setUpLabelPanel(labelPanel);
        setTerminal();
    }
    private void setupLHS(JPanel main, JPanel panel1, JPanel panel2, JPanel panel3) {
        Border line = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        main.setLayout(new GridLayout(GRIDROWS, GRIDCOLS));
        main.add(panel1);
        main.add(panel3);
        main.add(panel2);
        main.setBorder(line);
        contentPane.add(main, BorderLayout.WEST);
    }
    private void setupInventoryPanel(JPanel panel) {
        panel.setLayout(new BorderLayout());
        Border line = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        JLabel titleLabel = new JLabel("Backpack");
        titleLabel.setForeground(Color.GREEN);
        inventory = new JTextArea();
        inventory.setBackground(Color.BLACK);
        inventory.setForeground(Color.GREEN);
        JScrollPane sp = new JScrollPane(inventory);
        sp.setForeground(Color.GREEN);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(sp, BorderLayout.CENTER);
        inventory.setText("Empty");
        panel.setBorder(line);
        panel.setBackground(Color.DARK_GRAY);
    }
    private void setupEquippedPanel(JPanel panel) {
        panel.setLayout(new BorderLayout());
        Border line = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        JLabel titleLabel = new JLabel("Equipped Items");
        titleLabel.setForeground(Color.GREEN);
        equippedItems = new JTextArea();
        equippedItems.setBackground(Color.BLACK);
        equippedItems.setForeground(Color.GREEN);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(equippedItems, BorderLayout.CENTER);
        panel.setBorder(line);
        panel.setBackground(Color.DARK_GRAY);
    }
    private void setupCommandHistory(JPanel panel) {
        Border line = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        JLabel titleLabel = new JLabel("Game Output");
        titleLabel.setForeground(Color.GREEN);
        commandHistory = new JTextArea();
        commandHistory.setBackground(Color.BLACK);
        commandHistory.setForeground(Color.GREEN);
        JScrollPane sp = new JScrollPane(commandHistory);
        sp.setForeground(Color.GREEN);
        panel.setLayout(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.setBorder(line);
        panel.setBackground(Color.DARK_GRAY);
        panel.add(sp, BorderLayout.CENTER);
    }
    private void setUpLabelPanel(JPanel thePanel) {
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        JLabel exampleLabel = new JLabel("Enter A command:");
        exampleLabel.setForeground(Color.GREEN);
        nameLabel = new JLabel(" ");
        enterCommand = new JButton("Submit Command");
        enterCommand.setEnabled(false);
        cmdAL(enterCommand);
        dataEntry = new JTextField("", CONSTANT1);
        dataEntry.setEnabled(false);
        thePanel.setBorder(prettyLine);
        thePanel.add(nameLabel);
        thePanel.add(exampleLabel);
        thePanel.add(dataEntry);
        thePanel.add(enterCommand);
        thePanel.setBackground(Color.DARK_GRAY);
        contentPane.add(thePanel, BorderLayout.SOUTH);
    }
    private void cmdAL(JButton cmdBtn) {
        cmdBtn.addActionListener((ActionEvent ev) -> {
            if (dataEntry.getText().length() > 0) {
                userInput = dataEntry.getText().charAt(0);
                userInput = getCharInputType(userInput);
            }
            if (dataEntry.getText().length() <= 0) {
                commandHistory.setText("INVALID INPUT >:(");
            }
        });
    }
    private void makeMenuBar(Container cPane) {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Game Options");
        JMenu saveMenu = new JMenu("Save/Load");
        JMenuItem changeName = new JMenuItem("Change Player Name");
        JMenuItem openItem = new JMenuItem("Open New Adventure");
        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem loadGame = new JMenuItem("Load Game");
        JMenuItem quitItem = new JMenuItem("Quit");
        JMenuItem rMode = new JMenuItem("Rave Mode?");
        gameMenuListeners(fileMenu, changeName, quitItem, openItem, rMode);
        slMenuListeners(saveMenu, saveGame, loadGame);
        setMenuColor(fileMenu, saveMenu);
        setMenuItemsColor(changeName, openItem, saveGame, loadGame, quitItem);
        menuBar.add(fileMenu);
        menuBar.add(saveMenu);
        menuBar.setBackground(Color.DARK_GRAY);
        cPane.add(menuBar, BorderLayout.NORTH);
    }
    private void setMenuColor(JMenu m1, JMenu m2) {
        m1.setForeground(Color.GREEN);
        m1.setBackground(Color.DARK_GRAY);
        m2.setForeground(Color.GREEN);
        m2.setBackground(Color.DARK_GRAY);
    }
    private void setMenuTextColor(JMenuItem menuItem) {
        menuItem.setForeground(Color.GREEN);
        menuItem.setBackground(Color.DARK_GRAY);
    }
    private void setMenuItemsColor(JMenuItem m1, JMenuItem m2, JMenuItem m3, JMenuItem m4, JMenuItem m5) {
        setMenuTextColor(m1);
        setMenuTextColor(m2);
        setMenuTextColor(m3);
        setMenuTextColor(m4);
        setMenuTextColor(m5);
    }
    private void quitAL(JMenuItem quit) {
        quit.addActionListener(ev -> System.exit(0));
    }
    private void nameAl(JMenuItem name) {
        name.addActionListener((ActionEvent ev) -> {
            JFrame frame = new JFrame("Tweak");
            JPanel namePanel = new JPanel();
            frame.setSize(EFRAME_WIDTH, EFRAME_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JButton okBtn = new JButton("Enter");
            JTextField entry = new JTextField();
            enterBtnListener(frame, entry, okBtn);
            namePanel.setLayout(new FlowLayout());
            namePanel.add(entry);
            namePanel.add(okBtn);
            frame.add(namePanel);
            frame.setVisible(true);
        });
    }
    private void enterBtnListener(JFrame fr, JTextField ent, JButton eBtn) {
        ent.setText("Enter your new name");
        eBtn.addActionListener((ActionEvent ev) -> {
            Player p = theGame.getPlayer();
            if (p == null) {
                fr.setVisible(false);
                JOptionPane.showMessageDialog(this, "Player no exist bro", "ERROR >:(",  JOptionPane.ERROR_MESSAGE);
                commandHistory.setText("ERROR >:(");
            } else {
                p.setName(ent.getText());
                nameLabel.setText("Player name: " + ent.getText());
                fr.setVisible(false);
            }
        });
    }
    private void openAL(JMenuItem open) {
        open.addActionListener((ActionEvent ev) -> {
            JFileChooser fc = new JFileChooser();
            commandHistory.setText("Open a .JSON file...");
            if (fc.showOpenDialog(contentPane) == fc.APPROVE_OPTION) {
                String fn = fc.getSelectedFile().toString();
                RogueParser parser = new RogueParser("fileLocations.json", fn);
                theGame = new Rogue(parser);
            }
            commandHistory.setText("JSON file read successfully...");
            }
        );
    }
    private void saveAL(JMenuItem save) {
        save.addActionListener((ActionEvent ev) -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(contentPane) == fc.APPROVE_OPTION) {
                String fn = fc.getSelectedFile().toString();
                save(fn);
            }
        });
    }
    private void loadAL(JMenuItem load) {
        load.addActionListener((ActionEvent ev) -> {
            JFileChooser fc = new JFileChooser();
            commandHistory.setText("Open a .state file");
            if (fc.showOpenDialog(contentPane) == fc.APPROVE_OPTION) {
                String fn = fc.getSelectedFile().toString();
                loadGame(fn);
            }
        });
    }
    private void raveAl(JMenuItem rave) {
        rave.setBackground(Color.DARK_GRAY);
        rave.setForeground(Color.GREEN);
        rave.addActionListener((ActionEvent ev) -> {
            updateRaveMode();
        });
    }
    private boolean updateRaveMode() {
        if (raveMode) {
            commandHistory.setText(commandHistory.getText() + "\n PARTY HAS BEEN STOPPED");
            raveMode = false;
            return raveMode;
        }
        commandHistory.setText(commandHistory.getText() + "\n DONT STOP THE PARTY");
        raveMode = true;
        return raveMode;
    }
    private void slMenuListeners(JMenu menu, JMenuItem s, JMenuItem l) {
        saveAL(s);
        loadAL(l);
        addToMenu(menu, s);
        addToMenu(menu, l);
    }
    private void gameMenuListeners(JMenu menu, JMenuItem cn, JMenuItem q, JMenuItem o, JMenuItem rm) {
        quitAL(q);
        openAL(o);
        nameAl(cn);
        raveAl(rm);
        addToMenu(menu, cn);
        addToMenu(menu, o);
        addToMenu(menu, q);
        addToMenu(menu, rm);
    }
    private void addToMenu(JMenu menu, JMenuItem item) {
        menu.add(item);
    }
    private void addCmdHistory(String message) {
        String tempcmd = commandHistory.getText();
        tempcmd += "\n"  + message;
        commandHistory.setText(tempcmd);
    }
    private void setWindowDefaults(Container cPane) {
        setTitle("Absolutely Scuffed Nethack");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cPane.setLayout(new BorderLayout());
    }
    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminalPanel.setSize(COLS, ROWS);
        terminalPanel.setBackground(Color.DARK_GRAY);
        terminal.setCursorPosition(startCol, startCol);
        terminal.setCursorVisible(true);
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel, BorderLayout.CENTER);
    }
    private void start() {
        try {
            screen = new TerminalScreen(terminal);
            // screen = new VirtualScreen(baseScreen);
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Prints a string to the screen starting at the indicated column and row.
     * @param toDisplay the string to be printed
     * @param column    the column in which to start the display
     * @param row       the row in which to start the display
     **/
    public void putString(String toDisplay, int column, int row) {

        Terminal t = screen.getTerminal();
        try {
            t.setCursorPosition(column, row);

            for (char ch : toDisplay.toCharArray()) {
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Changes the message at the top of the screen for the user.
     * @param msg the message to be displayed
     **/
    public void setMessage(String msg) {
        putString("                                                ", 1, 1);
        putString(msg, startCol, msgRow);
    }
    /**
     * Redraws the whole screen including the room and the message.
     * @param message the message to be displayed at the top of the room
     * @param room the room map to be drawn
     **/
    public void draw(String message, String room) {
        try {
            screen.getTerminal().clearScreen();
            //setMessage(message);
            putString(room, 0, 0);
            screen.refresh();
            screen.getTerminal().setForegroundColor(new TextColor.RGB(0, MAXG, 0));
            if (raveMode) {
                screen.getTerminal().setForegroundColor(randomColor());
            }
            setJtaText(inventory);
            addCmdHistory(message);
        } catch (IOException e) {

        }
    }
    private TextColor randomColor() {
        int r = 0;
        int g = 0;
        int b = 0;
        Random rand = new Random();
        if (r == 0 && g == 0 && b == 0) { // black color
            r = IH8_CHECKSTYLE - rand.nextInt(IH8_CHECKSTYLE);
            g = IH8_CHECKSTYLE - rand.nextInt(IH8_CHECKSTYLE);
            b = IH8_CHECKSTYLE - rand.nextInt(IH8_CHECKSTYLE);
        }
        TextColor randColor = new TextColor.RGB(r, g, b);
        return randColor;
    }
    /**
     * Obtains input from the user and returns it as a char. Converts arrow keys to
     * the equivalent movement keys in rogue.
     * @return the ascii value of the key pressed by the user
     **/
    public char getInput() {
        KeyStroke keyStroke = null;
        char returnChar;
        while (keyStroke == null) {
            try {
                keyStroke = screen.pollInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (keyStroke.getKeyType() == KeyType.Character) {
            return getCharInputType(keyStroke);
        }
        returnChar = getKSInputType(keyStroke);
        return returnChar;
    }
    private Character getCharInputType(char rc) {
        if (rc == 's' || rc == 'w' || rc == 'a' || rc == 'd') {
            return movementHandler(rc); // constant defined in rogue
        } else if (rc == 'w' || rc == 'e' || rc == 't') {
            return commandHandler(rc);
        }
        return rc;
    }
    private Character getKSInputType(KeyStroke ks) {
        if (ks.getKeyType() == KeyType.ArrowDown) {
            return Rogue.DOWN; // constant defined in rogue
        } else if (ks.getKeyType() == KeyType.ArrowUp) {
            return Rogue.UP;
        } else if (ks.getKeyType() == KeyType.ArrowLeft) {
            return Rogue.LEFT;
        } else if (ks.getKeyType() == KeyType.ArrowRight) {
            return Rogue.RIGHT;
        }
        return ks.getCharacter();
    }
    private Character getCharInputType(KeyStroke ks) {
        char rc = ks.getCharacter();
        if (rc == 'j' || rc == 'i' || rc == 'k' || rc == 'l') {
            return movementHandler(rc); // constant defined in rogue
        } else if (rc == 'w' || rc == 'e' || rc == 't') {
            return commandHandler(rc);
        }
        return rc;
    }
    private Character movementHandler(char rc) {
        if (rc == 'k') {
            return Rogue.DOWN; // constant defined in rogue
        } else if (rc == 'i') {
            return Rogue.UP;
        } else if (rc == 'j') {
            return Rogue.LEFT;
        } else if (rc == 'l') {
            return Rogue.RIGHT;
        }
        return rc;
    }
    private Character commandHandler(char rc) {
        JFrame inv;
        if (rc == 'w') { // wear item
            rc = 'w';
            inv = createInventoryFrame("wear");
            inv.setVisible(true);
        } else if (rc == 't') { // toss item
            rc = 't';
            inv = createInventoryFrame("toss");
            inv.setVisible(true);
        } else if (rc == 'e') { // eat item
            rc = 'e';
            inv = createInventoryFrame("eat");
            inv.setVisible(true);
        }
        return rc;
    }
    private void wearItem(int idx) {
        Player p = theGame.getPlayer();
        Item i = p.getInventory().get(idx);
        if (p == null) {
            commandHistory.setText(commandHistory.getText() + "\nPlayer doesnt exist");
        }
        if (!putSomeClothesOnWierdo(i, p)) {
            JOptionPane.showMessageDialog(this, "Cannot wear " + i.getName());
            commandHistory.setText(commandHistory.getText() + "\nItem unwearable");
        }
        setJtaText(inventory);
    }
    private boolean putSomeClothesOnWierdo(Item i, Player p) { // put some clothes on you wierdo
        if (i.getType().equals("Ring")) {
            Ring r = (Ring) i;
            commandHistory.setText(commandHistory.getText() + r.wear());
            p.getInventory().remove(r);
            setJtaText(inventory);
            equippedItems.setText(equippedItems.getText() + "\n" + i.getName());
            return true;
        }
        if (i.getType().equals("Clothing")) {
            Clothing c = (Clothing) i;
            commandHistory.setText(commandHistory.getText() + c.wear());
            p.getInventory().remove(c);
            setJtaText(inventory);
            equippedItems.setText(equippedItems.getText() + "\n" + i.getName());
            return true;
        }
        return false;
    }
    private void tossItem(int idx) {
        Player p = theGame.getPlayer();
        Item i = p.getInventory().get(idx);
        if (p == null) {
            commandHistory.setText(commandHistory.getText() + "\nPlayer doesnt exist");
        }
        if (!yeetItem(i)) {
            JOptionPane.showMessageDialog(this, "Cannot toss " + i.getName());
            commandHistory.setText(commandHistory.getText() + "\nItem untossable");
        }
        setJtaText(inventory);
    }
    private boolean yeetItem(Item i) {
        Player pl = theGame.getPlayer();
        Room r = pl.getCurrentRoom();
        if (i.getType().equals("Potion")) {
            Potion pot = (Potion) i;
            commandHistory.setText(commandHistory.getText() + pot.toss());
            pl.getInventory().remove(i);
            yeetItemPt2(r, i);
            return true;
        }
        if (i.getType().equals("SmallFood")) {
            SmallFood smFood = (SmallFood) i;
            commandHistory.setText(commandHistory.getText() + smFood.toss());
            pl.getInventory().remove(i);
            yeetItemPt2(r, i);
            return true;
        }
        return false;
    }
    private void yeetItemPt2(Room r, Item i) {
        Random rand = new Random();
        int x = r.getWidth() - rand.nextInt(r.getWidth());
        int y = r.getHeight() - rand.nextInt(r.getHeight());
        x = verifyX(x, r);
        y = verifyY(y, r);
        i.setXyLocation(new Point((int) x, (int) y));
        r.addItemToRoom(i);
    }
    private int verifyX(int x, Room r) {
        if (x == 0) {
            x++;
        }
        if (x == r.getWidth()) {
            x--;
        }
        return x;
    }
    private int verifyY(int y, Room r) {
        if (y == 0) {
            y++;
        }
        if (y == r.getHeight()) {
            y--;
        }
        return y;
    }
    private void eatItem(int idx) {
        Player p = theGame.getPlayer();
        Item i = p.getInventory().get(idx);
        if (p == null) {
            commandHistory.setText(commandHistory.getText() + "\nPlayer doesnt exist");
        }
        if (!munchies(i, p)) {
            JOptionPane.showMessageDialog(this, "Cannot eat " + i.getName());
        }
    }
    private boolean munchies(Item i, Player p) {
        if (i.getType().equals("SmallFood") || i.getType().equals("Food")) {
            Food fd = (Food) i;
            commandHistory.setText(commandHistory.getText() + fd.eat());
            p.getInventory().remove(i);
            return true;
        }
        if (i.getType().equals("Potion")) {
            Potion pot = (Potion) i;
            commandHistory.setText(commandHistory.getText() + pot.eat());
            p.getInventory().remove(i);
            return true;
        }
        return false;
    }
    private JFrame createInventoryFrame(String type) {
        JFrame jf = new JFrame();
        JPanel invPanel = new JPanel();
        invPanel.setLayout(new BorderLayout());
        JButton itemBtn = new JButton("Select Item");
        JTextArea invArea = new JTextArea();
        invArea.setEditable(false);
        JLabel invLabel = new JLabel("Backpack");
        JTextField invSelect = new JTextField("Enter Item number");
        addToInvPanel(invPanel, invArea, invLabel, itemBtn, invSelect);
        itemBtnListener(jf, invSelect, itemBtn, type);
        jf.add(invPanel);
        jf.pack();
        return jf;
    }
    private void setJtaText(JTextArea jta) {
        ArrayList<String> playerInv = theGame.getPlayer().displayInventory();
        String temp = "";
        for (String s: playerInv) {
            temp += s;
        }
        jta.setText(temp);
    }
    private void addToInvPanel(JPanel inv, JTextArea jta, JLabel jl, JButton jb, JTextField jtf) {
        JPanel jp = new JPanel();
        JPanel jtaPan = new JPanel();
        jp.setLayout(new BorderLayout());
        jtaPan.add(jl, BorderLayout.NORTH);
        jtaPan.add(jta, BorderLayout.CENTER);
        setJtaText(jta);
        jp.add(jtf, BorderLayout.NORTH);
        jp.add(jb, BorderLayout.SOUTH);
        inv.add(jtaPan, BorderLayout.CENTER);
        inv.add(jp, BorderLayout.SOUTH);
    }
    private void itemBtnListener(JFrame jf, JTextField jtf, JButton item, String type) {
        item.addActionListener((ActionEvent ev) -> {
            jf.setVisible(false);
            if (validateInput(jtf)) {
                int index = Integer.parseInt(jtf.getText());
                Player p =  theGame.getPlayer();
                if (p.getInventory().size() == 0) {
                    jtf.setEditable(false);
                    item.setEnabled(false);
                }
                if (index >= p.getInventory().size() || index < 0) {
                    jtf.setText("Invalid number (0-InventorySize)");
                } else {
                    Item newItem = p.getInventory().get(index);
                    addCmdHistory("You selected: " + newItem.getName() + "\n");
                    handleFunction(index, type);
                }
            }
        });
    }
    private void handleFunction(int index, String type) {
        if (type.equals("wear")) {
            wearItem(index);
        }
        if (type.equals("eat")) {
            eatItem(index);
        }
        if (type.equals("toss")) {
            tossItem(index);
        }
    }
    private boolean validateInput(JTextField jtf) {
        if (jtf.getText().length() > 2) {
            return false;
        }
        return true;
    }
    private static void handleInput(char userI, String message, Rogue tGame, WindowUI theGameUI) {
        try {
            message = tGame.makeMove(userI);
        } catch (InvalidMoveException badMove) {
            message = "I didn't understand what you meant, please enter a command";
        }
        theGameUI.setMessage(message);
        theGameUI.draw(message, tGame.getNextDisplay());
    }
    /**
     * Saves the game to a binary file using serialization.
     */
    public void save() {
        String fileName = "rogueGame.state";
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream outputDest = new ObjectOutputStream(outputStream);
            outputDest.writeObject(theGame);
            outputStream.close();
            outputDest.close();
            JOptionPane.showMessageDialog(this, "Successfully Created Save State");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Save state creation failed", "ERROR",  JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Saves the game using a generated file name.
     * @param fileName (String) the file name
     */
    public void save(String fileName) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream outputDest = new ObjectOutputStream(outputStream);
            outputDest.writeObject(theGame);
            outputStream.close();
            outputDest.close();
            JOptionPane.showMessageDialog(this, "Successfully created save state");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Save state creation failed", "ERROR",  JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Loads a save state of the game.
     * @param fileName (String) the file containing the save state
     */
    public void loadGame(String fileName) {
        try {
            FileInputStream fi = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fi);
            theGame = (Rogue) in.readObject();
            fi.close();
            in.close();
            JOptionPane.showMessageDialog(this, "Successfully loaded save state");
            commandHistory.setText(commandHistory.getText() + "\nLoad save");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Load save state failed ", "ERROR",  JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Load save state failed", "ERROR",  JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void initName(Player p) {
        nameLabel.setText("Player Name: " + p.getName());
        nameLabel.setForeground(Color.GREEN);
    }
    /**
     * The controller method for making the game logic work.
     * @param args command line parameters
     **/
    public static void main(String[] args) {
        userInput = 'h';
        String message = "Welcome to my Rogue game";
        String configurationFileLocation = "fileLocations.json";
        RogueParser parser = new RogueParser(configurationFileLocation);
        WindowUI theGameUI = new WindowUI();
        theGameUI.setSize(WIDTH, HEIGHT);
        theGame = new Rogue(parser);
        Player thePlayer = new Player("Judi");
        initName(thePlayer);
        theGame.setPlayer(thePlayer);
        theGameUI.draw(message, theGame.getNextDisplay());
        theGameUI.setVisible(true);
        while (userInput != 'q') {
            userInput = theGameUI.getInput();
            handleInput(userInput, message, theGame, theGameUI);
        }
        theGameUI.save();
        System.exit(0);
    }
}
