# konnpetkoll
Collection of controls for Java.<br />
KonnpetKoll stands for a stylized Complete Collection with its ready to use components.
## How to use
In your form, import a new Bean and call it ```org.wingate.konnpetkoll.swing.<wanted-component>``` and use this bean into your IDE form view. You can also use it directly in your code with an import.
## Components

KKPrTable :

![KKPrTable](https://github.com/TW2/konnpetkoll/blob/main/screenshots/01.png)
```Java
scrollTable = new JScrollPane(table);
scrollTable.setLocation(100, 10);
scrollTable.setSize(300, 200);
getContentPane().add(scrollTable);

table.addLine("Text color", java.awt.Color.yellow);
table.addLine("Karaoke color", java.awt.Color.red);
table.addLine("Outline color", java.awt.Color.blue);
table.addLine("Shadow color", java.awt.Color.black);
KKList<String> fruits = new KKList<>();
fruits.getList().add("Pineapple");
fruits.getList().add("Apple");
fruits.getList().add("Banana");
table.addLine("Fruit", fruits);
table.addLine("Happy", new KKCheckBox());
table.addLine("Happy", "Fairy Tail");
table.addLine("Integer", 0);
table.addLine("Double", 0d);
table.addLine("Color", new KKWithDialog(java.awt.Color.yellow));
```

PlaceholderTextField :

![PlaceholderTextField](https://github.com/TW2/konnpetkoll/blob/main/screenshots/02.png)

Waveform :

![Waveform](https://github.com/TW2/konnpetkoll/blob/main/screenshots/03.png)

Spectrogram :

![Spectrogram](https://github.com/TW2/konnpetkoll/blob/main/screenshots/04.png)
