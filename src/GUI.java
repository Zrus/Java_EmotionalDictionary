import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

class GUI extends JFrame {

    //Function buttons.
    private JToggleButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JToggleButton button6;
    private JButton button7;

    //Main workspace components.
    private JTextField textField;
    private DefaultListModel[] defaultListModel;
    private JList listEmoji;
    private JTextArea textArea;
    private Data db;

    GUI() {
        super();
        setTitle("Emotional Dictionary");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 450));
        setLayout(new BorderLayout());
        setVisible(true);
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                try {
//                    db.saveData();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                super.windowClosing(e);
//            }
//        });
    }

    void initUI(Data data) {
        db = data;
        initMenuBar();
        initToolsBar();
        initMainWorkspace();
        pack();
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("History");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button6.doClick();
            }
        });
        JMenuItem item2 = new JMenuItem("Save all");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    db.saveData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JMenuItem item3 = new JMenuItem("Exit");
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem item4 = new JMenuItem("Add icon");
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2.doClick();
            }
        });
        JMenuItem item5 = new JMenuItem("Remove icon");
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button3.doClick();
            }
        });
        JMenuItem item6 = new JMenuItem("Edit icon");
        item6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button4.doClick();
            }
        });
        JMenuItem item7 = new JMenuItem("Rename icon");
        item7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button5.doClick();
            }
        });
        JMenuItem item8 = new JMenuItem("Export icon");
        item8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button7.doClick();
            }
        });
        editMenu.add(item4);
        editMenu.add(item5);
        editMenu.add(item6);
        editMenu.add(item7);
        editMenu.add(item8);

        JMenu findMenu = new JMenu("Find");
        JCheckBoxMenuItem checkBoxMenuItem1 = new JCheckBoxMenuItem("Icon");
        JCheckBoxMenuItem checkBoxMenuItem2 = new JCheckBoxMenuItem("Content");
        checkBoxMenuItem1.setState(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(checkBoxMenuItem1);
        buttonGroup.add(checkBoxMenuItem2);

        findMenu.add(checkBoxMenuItem1);
        findMenu.add(checkBoxMenuItem2);

        buttonGroup.getSelection();

        JMenu helpMenu = new JMenu("Help");
        JMenuItem item11 = new JMenuItem("About");
        item11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initAboutUI();
            }
        });
        helpMenu.add(item11);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(findMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void initToolsBar() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        button1 = new JToggleButton("Find");
        button1.setPreferredSize(new Dimension(100, 25));
        button1.setToolTipText("Find emoji");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.requestFocus();
                button6.setSelected(false);
                filter();
            }
        });

        button2 = new JButton("Add");
        button2.setPreferredSize(new Dimension(100, 25));
        button2.setToolTipText("Add emoji");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button6.setSelected(false);
                filter();
                initAddIconUI();
            }
        });

        button3 = new JButton("Remove");
        button3.setPreferredSize(new Dimension(100, 25));
        button3.setToolTipText("Remove emoji");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button6.isSelected()) {
                    button6.setSelected(false);
                    filter();
                }
                initRemoveIconUI();
            }
        });

        button4 = new JButton("Edit");
        button4.setPreferredSize(new Dimension(100, 25));
        button4.setToolTipText("Edit emoji");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button6.isSelected()) {
                    button6.setSelected(false);
                    filter();
                }
                initEditDescriptionUI();
            }
        });

        button5 = new JButton("Rename");
        button5.setPreferredSize(new Dimension(100, 25));
        button5.setToolTipText("Rename emoji");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button6.isSelected()) {
                    button6.setSelected(false);
                    filter();
                }
                initRenameIconUI();
            }
        });

        button6 = new JToggleButton("History");
        button6.setPreferredSize(new Dimension(100, 25));
        button6.setToolTipText("Finding history");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button6.isSelected())
                    showHistory();
                else
                    filter();
            }
        });

        button7 = new JButton("Export");
        button7.setPreferredSize(new Dimension(100, 25));
        button7.setToolTipText("Export emoji");
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    exportIcon();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonsPanel.add(button1);
        buttonsPanel.add(button2);
        buttonsPanel.add(button3);
        buttonsPanel.add(button4);
        buttonsPanel.add(button5);
        buttonsPanel.add(button6);
        buttonsPanel.add(button7);

        add(buttonsPanel, BorderLayout.NORTH);
    }

    private void initMainWorkspace() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(250, mainPanel.getHeight()));
        JPanel textFieldPanel = new JPanel(new BorderLayout());
        textFieldPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Find"));
        JPanel listEmojiPanel = new JPanel(new BorderLayout());
        listEmojiPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Icon"));
        JScrollPane listEmojiScrollPane;

        textField = new JTextField();
        textField.setBorder(BorderFactory.createCompoundBorder(textField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
        textField.setPreferredSize(new Dimension(leftPanel.getWidth(), 25));
        textFieldPanel.add(textField, BorderLayout.NORTH);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                listEmoji.clearSelection();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        defaultListModel = new DefaultListModel[]{createDefaultModelList(db.keySet())};
        listEmoji = new JList(defaultListModel[0]);
        listEmoji.setBorder(BorderFactory.createCompoundBorder(listEmoji.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        listEmojiScrollPane = new JScrollPane(listEmoji);
        listEmojiPanel.add(listEmojiScrollPane, BorderLayout.CENTER);

        leftPanel.add(textFieldPanel, BorderLayout.NORTH);
        leftPanel.add(listEmojiPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Description"));
        JScrollPane textAreaScrollPane;
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(textArea.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        textAreaScrollPane = new JScrollPane(textArea);

        rightPanel.add(textAreaScrollPane);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        listEmoji.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selectedItem = null;
                try {
                    selectedItem = listEmoji.getSelectedValue().toString();
                } catch (Exception ex) {};
                textArea.setText(db.get(selectedItem));
                db.writeHistory(selectedItem);
            }
        });

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }

    private void filter() {
        if (textField.getText().isEmpty()) {
            defaultListModel[0] = createDefaultModelList(db.keySet());
            listEmoji.setModel(defaultListModel[0]);
            return;
        }

        String searchTerm = textField.getText();
        DefaultListModel<String> filteredItems = new DefaultListModel<>();
        String[] data = db.keySet();

        if (!button1.isSelected()) {
            for(String key: data) {
                if (key.contains(searchTerm.toUpperCase())) {
                    filteredItems.addElement(key);
                }
            }
        } else {
            for(String key: data) {
                if (db.get(key).contains(searchTerm.toLowerCase())) {
                    filteredItems.addElement(key);
                }
            }
        }

        listEmoji.setModel(filteredItems);
    }

    private DefaultListModel<String> createDefaultModelList(String[] key_set) {
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for (String key: key_set) {
            defaultListModel.addElement(key);
        }
        return defaultListModel;
    }

    private void initAddIconUI() {
        JFrame addIconFrame = new JFrame();
        addIconFrame.setTitle("Add new icon");
        addIconFrame.setLayout(new BorderLayout());
        addIconFrame.setPreferredSize(new Dimension(450, 200));
        addIconFrame.setResizable(false);
        addIconFrame.setVisible(true);

        JPanel mainAddIconPanel = new JPanel(new BorderLayout());

        JPanel iconTFPanel = new JPanel(new BorderLayout());
        iconTFPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        JLabel iconLabel = new JLabel("Icon:");
        iconLabel.setPreferredSize(new Dimension(100, 25));
        JTextField iconTF = new JTextField();
        iconTF.setBorder(BorderFactory.createCompoundBorder(iconTF.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
        iconTFPanel.add(iconLabel, BorderLayout.WEST);
        iconTFPanel.add(iconTF, BorderLayout.CENTER);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        JLabel descriptionLabel = new JLabel("Description: ");
        descriptionLabel.setPreferredSize(new Dimension(100, 25));
        JTextArea descriptionTA = new JTextArea();
        descriptionTA.setLineWrap(true);
        descriptionTA.setBorder(BorderFactory.createCompoundBorder(descriptionTA.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTA);
        descriptionPanel.add(descriptionLabel, BorderLayout.WEST);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder());
        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 25));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
        buttonsPanel.add(addButton);
        buttonsPanel.add(cancelButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!iconTF.getText().isEmpty() && !descriptionTA.getText().isEmpty()) {
                    db.put(iconTF.getText(), descriptionTA.getText());
                }
                filter();
                addIconFrame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIconFrame.dispose();
            }
        });

        mainAddIconPanel.add(iconTFPanel, BorderLayout.NORTH);
        mainAddIconPanel.add(descriptionPanel, BorderLayout.CENTER);
        mainAddIconPanel.add(buttonsPanel, BorderLayout.SOUTH);

        addIconFrame.add(mainAddIconPanel);
        addIconFrame.pack();
    }

    private void initRemoveIconUI() {
        if (listEmoji.isSelectionEmpty())
            return;
        int input = JOptionPane.showConfirmDialog(null,
                "Do you want to remove this icon?",
                "Remove icon",
                JOptionPane.OK_CANCEL_OPTION);
        if (input == 0) {
            db.remove(listEmoji.getSelectedValue().toString());
        }
        filter();
    }

    private void initEditDescriptionUI() {
        if (listEmoji.isSelectionEmpty())
            return;

        JFrame editDescription = new JFrame();
        editDescription.setTitle("Edit icon's descritption");
        editDescription.setLayout(new BorderLayout());
        editDescription.setPreferredSize(new Dimension(450,200));
        editDescription.setResizable(false);
        editDescription.setVisible(true);

        JPanel editPanel = new JPanel(new BorderLayout());
        editPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        JLabel editLabel = new JLabel("Description:");
        editLabel.setPreferredSize(new Dimension(100 ,25));
        JTextArea editTA = new JTextArea();
        editTA.setText(db.get(listEmoji.getSelectedValue().toString()));
        editTA.setLineWrap(true);
        editTA.setBorder(BorderFactory.createCompoundBorder(editTA.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JScrollPane editScrollPane = new JScrollPane(editTA);
        editPanel.add(editLabel, BorderLayout.WEST);
        editPanel.add(editScrollPane, BorderLayout.CENTER);
        editDescription.add(editPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(100, 25));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        editDescription.add(buttonsPanel, BorderLayout.SOUTH);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editTA.getText().isEmpty()) {
                    db.changeValue(listEmoji.getSelectedValue().toString(), editTA.getText());
                    filter();
                }
                editDescription.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDescription.dispose();
            }
        });

        editDescription.pack();
    }

    private void initRenameIconUI() {
        if (listEmoji.isSelectionEmpty())
            return;

        JFrame renameFrame = new JFrame();
        renameFrame.setTitle("Rename Icon");
        renameFrame.setLayout(new BorderLayout());
        renameFrame.setPreferredSize(new Dimension(450, 100));
        renameFrame.setResizable(false);
        renameFrame.setVisible(true);

        JPanel renamePanel = new JPanel(new BorderLayout());
        renamePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        JLabel renameLabel = new JLabel("New icon's name: ");
        renameLabel.setPreferredSize(new Dimension(150, 25));
        JTextField renameTF = new JTextField();
        renameTF.setBorder(BorderFactory.createCompoundBorder(renameTF.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
        renamePanel.add(renameLabel, BorderLayout.WEST);
        renamePanel.add(renameTF, BorderLayout.CENTER);
        renameFrame.add(renamePanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(100, 25));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        renameFrame.add(buttonsPanel, BorderLayout.SOUTH);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String new_key = renameTF.getText();
                if (!renameTF.getText().isEmpty() && !db.containsKey(new_key)) {
                    String key = listEmoji.getSelectedValue().toString();
                    String description = db.get(key);
                    db.remove(key);
                    db.put(new_key, description);
                    filter();
                }
                renameFrame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renameFrame.dispose();
            }
        });

        renameFrame.pack();
        filter();
    }

    private void showHistory() {
        DefaultListModel<String> historyListModel = createDefaultModelList(db.keySetHistory());
        listEmoji.setModel(historyListModel);
    }

    private void exportIcon() throws IOException {
        db.export(listEmoji.getSelectedValue().toString());
    }

    private void initAboutUI() {
        JOptionPane.showMessageDialog(this,
                "Emotional Dictionary 1.0 by Zrus",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
}