import java.awt.*;

public class Help {
    public void start(String[] args){
        if (args.length < 2){
            printHelp();
            return;
        }
        switch (args[1].toLowerCase()){
            case "fonts" -> getFontsInSystem();
            case "colors" -> getColors();
            case "positioning" -> getBoxPosition();
        }
    }

    public  void getFontsInSystem(){
        System.out.println("Список поддерживаемых шрифтов");
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for ( int i = 2; i < fonts.length; i+=3 )
        {
            System.out.format("%-32s%-32s%-32s",fonts[i-2], fonts[i-1], fonts[i]);
            System.out.println();
        }
        if(fonts.length%3==2){
            System.out.format("%-32s%-32s%",fonts[fonts.length-2],fonts[fonts.length-1]);
        } else if (fonts.length%3==1){
            System.out.format("%-32s",fonts[fonts.length-1]);
        }
    }

    public void getBoxPosition(){
        System.out.println("Список готовых позиций");
        System.out.format("%-32s%-32s%-32s","TOP LEFT","TOP","TOP RIGHT");
        System.out.println();
        System.out.format("%-32s%-32s%-32s","LEFT","CENTER","RIGHT");
        System.out.println();
        System.out.format("%-32s%-32s%-32s","BOTTOM LEFT","BOTTOM","BOTTOM RIGHT");

    }

    public void getColors(){
        System.out.println("Список готовых цветов");
        System.out.println("WHITE \nLIGHT GRAY \nGRAY \nDARK GRAY \nBLACK \nRED \nPINK \nORANGE \nYELLOW \nGREEN \nMAGENTA \nCYAN \nBLUE");
    }

    public void printHelp(){
        System.out.println("Консольное приложение \u001B[32mATI \u001B[0m- Add Text to Image  " +
                "\n\u001B[32mhelp \u001B[0m- выводит список доступных команд." +
                "\n\u001B[32mresolution \u001B[33m[файл] \u001B[0m- выводит разрешение изображения." +
                "\n\u001B[32mmem \u001B[0m- команда служит для создания изображения с текстом." +
                "\n\u001B[32mmem \u001B[33m[файл] ['текст'] \u001B[0m- базовый вариант команды, добавляет текст к изображению, используя базовые параметры. Поддерживаются форматы изображения jpg или png." +
                "\nБазовые параметры  font \"Arial\" style \"PLAIN\" size \"14\" color \"BLACK\" position \"CENTER\"" +
                "\nПример команды:" +
                "\nmem ./cat.jpg 'Мяу' - создает изображение catNew.jpg с текcтом 'Мяу', используя базовые параметры." +
                "\n\nКоманда \u001B[32mmem \u001B[0mподдерживает множество аргументов"       +
                "\n\u001B[32mmem \u001B[33m[файл] ['текст'] \u001B[34mfont \u001B[33m[\"название_шрифта\"] \u001B[34mstyle \u001B[33m[\"название_стиля\"] \u001B[34msize \u001B[33m[\"размер_текста\"] \u001B[34mcolor \u001B[33m[\"цвет_текста\"] \u001B[34mposition \u001B[33m[\"позиция_текста\"] \u001B[0m- полный вариант команды. "  +
                "\n\n\u001B[34mfont \u001B[0m- изменяет шрифт текста. Введите \u001B[32mhelp fonts\u001B[0m, чтобы узнать список поддерживаемых шрифтов."     +
                "\nПример команды:" +
                "\nmem ./cat.jpg 'Мяу' font \"Calibri\" - создает изображение catNew.jpg с текcтом 'Мяу', используя базовые параметры и шрифт Calibri." +
                "\n\n\u001B[34mstyle \u001B[0m- изменяет стиль начертания  текста. Поддерживает такие параметры как: \u001B[33mPLAIN, BOLD, ITALIC, BOLD ITALIC\u001B[0m." +
                "\nПример команды:" +
                "\nmem ./cat.jpg 'Мяу' style \"ITALIC\" - создает изображение catNew.jpg с текcтом 'Мяу', используя базовые параметры и стиль начертания курсив."    +
                "\n\n\u001B[34msize \u001B[0m- изменяет размер текста." +
                "\nПример команды:" +
                "\nmem ./cat.jpg 'Мяу' size \"50\" - создает изображение catNew.jpg с текcтом 'Мяу', используя базовые параметры и размер текста 50."    +
                "\n\n\u001B[34mcolor \u001B[0m- изменяет цвет текста. Цвет задается посредством RGB или с использованием готовых цветов. Введите \u001B[32mhelp colors\u001B[0m, чтобы узнать список готовых цветов." +
                "\nПример команды:" +
                "\nmem ./cat.jpg 'Мяу' color \"BLACK\" - создает изображение catNew.jpg с текcтом 'Мяу', используя базовые параметры и черный цвет." +
                "\nmem ./cat.jpg 'Мяу' color \"255 175 175\" - создает изображение catNew.jpg с текcтом 'Мяу', используя базовые параметры и розовый цвет." +
                "\n\n\u001B[34mposition \u001B[0m- задает позицию текста. Задается посредством координат X и Y или с использованием готовых позиции. Введите \u001B[32mhelp positioning\u001B[0m, чтобы узнать список готовых позиций." +
                "\nПример команды:" +
                "\nmem ./cat.jpg 'Мяу' position \"150 480\" - создает изображение catNew.jpg с текcтом 'Мяу', используя базовые параметры и размещая текст на координатах (150,480)." +
                "\nmem ./cat.jpg 'Мяу' position \"BOTTOM RIGHT\" - создает изображение catNet.jpg с текcтом 'Мяу', используя базовые параметры и  размещая текст в нижнем правом углу." +
                "\n\nАргументы могут сочетаться в любом порядке, на самое главное чтобы команда начиналась с \u001B[32mmem\u001B[0m и " +
                "\nзаканчивалась аргументом \u001B[34mposition\u001B[0m(если аргумент \u001B[34mposition\u001B[0m используется в вашем запросе)." +
                "\nПример команды:"+
                "\nmem ./dog.png 'Гав' font \"Gigi\" color \"DARK GRAY\" size \"100\"  style \"BOLD\" position \"TOP LEFT\" " +
                "- \nсоздает изображение catNew.jpg с текcтом 'Гаф', используя шрифт Gigi, размер текста 100, стиль начертания жирный, темно-серого цвета в верхнем левом углу"
        );
    }
}
