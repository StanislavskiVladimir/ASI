import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mem {
    public void start(String[] args) {
        if (args.length < 3) {
            System.out.println("Неверные аргументы");
            return;
        }

        String path = args[1];

        int startMemIndex = 0, endMemIndex = 0;
        for (int i = 2; i < args.length; i++) {
            if (args[i].startsWith("'")) {
                startMemIndex = i;
            } else if (args[i].endsWith("'")) {
                endMemIndex = i;
                break;
            }
        }
        StringBuffer stringBuffer = new StringBuffer(args[startMemIndex]);
        for (int i = startMemIndex + 1; i < endMemIndex + 1; i++) {
            stringBuffer.append(" ");
            stringBuffer.append(args[i]);
        }
        String mem = stringBuffer.substring(1, stringBuffer.length() - 1);

        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Graphics2D g = img.createGraphics();
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.BLACK);

        for (int i = endMemIndex + 1; i < args.length; i += 2) {
            switch (args[i]) {
                case "font" -> setFont(g, args[i + 1]);
                case "style" -> setStyle(g, args[i + 1]);
                case "size" -> setSize(g, args[i + 1]);
                case "color" -> setColor(g, args[i + 1]);
                case "position" -> {
                    setPosition(g, img, path,mem, args[i + 1]);
                    return;
                }
            }
        }

        TextLayout textLayout = new TextLayout(mem, g.getFont(), g.getFontRenderContext());
        int x = (int)(img.getWidth()/2-textLayout.getBounds().getWidth()/2-textLayout.getBounds().getX());
        int y = (int)(img.getHeight()/2  + textLayout.getBounds().getHeight()/2);
        saveMem(g,img,path,mem,x,y);
    }

    public void setFont(Graphics2D g, String font) {
        if(!Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).contains(font)){
            System.out.println("Неправильно указан шрифт. Будет использован шрифт по умолчанию");
            return;
        };
        g.setFont(new Font(font, g.getFont().getStyle(), g.getFont().getSize()));

    }

    private void setSize(Graphics2D g, String size) {
        if(Float.parseFloat(size) < 0) {
            System.out.println("Неправильно указан размер. Будет использован размер по умолчанию");
            return;
        }
        g.setFont(g.getFont().deriveFont(Float.parseFloat(size)));
    }

    private void setStyle(Graphics2D g, String style) {
        switch (style.toUpperCase()) {
            case "PLAIN" -> g.setFont(g.getFont().deriveFont(Font.PLAIN));
            case "BOLD" -> g.setFont(g.getFont().deriveFont(Font.BOLD));
            case "ITALIC" -> g.setFont(g.getFont().deriveFont(Font.ITALIC));
            case "BOLD ITALIC", "ITALIC BOLD" -> g.setFont(g.getFont().deriveFont(Font.BOLD + Font.ITALIC));
            default ->
                    System.out.println("Неправильно указан вид начертания шрифта. Будет использован вид начертания по умолчанию");
        }
    }

    private void setColor(Graphics2D g, String color) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(color);
        if (matcher.find()) {
            int R = Integer.parseInt(matcher.group());
            matcher.find();
            int G = Integer.parseInt(matcher.group());
            matcher.find();
            int B = Integer.parseInt(matcher.group());
            if (R < 0 || R > 256 || G < 0 || G > 256 || B < 0 || B > 256) {
                System.out.println("Неправильно указан цвет в формате RGB. Будет использован цвет по умолчанию");
            } else {
                g.setColor(new Color(R, G, B));
            }
            return;
        }
        switch (color.toUpperCase()) {
            case "WHITE" -> g.setColor(Color.WHITE);
            case "LIGHT GRAY" -> g.setColor(Color.LIGHT_GRAY);
            case "GRAY" -> g.setColor(Color.GRAY);
            case "DARK GRAY" -> g.setColor(Color.DARK_GRAY);
            case "BLACK" -> g.setColor(Color.BLACK);
            case "RED" -> g.setColor(Color.RED);
            case "PINK" -> g.setColor(Color.PINK);
            case "ORANGE" -> g.setColor(Color.ORANGE);
            case "YELLOW" -> g.setColor(Color.YELLOW);
            case "GREEN" -> g.setColor(Color.GREEN);
            case "MAGENTA" -> g.setColor(Color.MAGENTA);
            case "CYAN" -> g.setColor(Color.CYAN);
            case "BLUE" -> g.setColor(Color.BLUE);
            default -> System.out.println("Неправильно указан цвет. Будет использован цвет по умолчанию");
        }
    }

    private void setPosition(Graphics2D g, BufferedImage img,String path, String mem, String position) {
        int x = 0, y = 0;
        TextLayout textLayout = new TextLayout(mem, g.getFont(), g.getFontRenderContext());
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(position);
        if (matcher.find()) {
            x = Integer.parseInt(matcher.group());
            matcher.find();
            y = Integer.parseInt(matcher.group());
            saveMem(g, img,path,mem, x, y);
            return;
        }

        switch (position.toUpperCase()) {
            case "TOP LEFT", "LEFT TOP" -> {
                x = (int) (0 - textLayout.getBounds().getX());
                y = (int) textLayout.getBounds().getHeight();
            }
            case "TOP" -> {
                x = (int) (img.getWidth() / 2 - textLayout.getBounds().getWidth() / 2 - textLayout.getBounds().getX());
                y = (int) textLayout.getBounds().getHeight();
            }
            case "TOP RIGHT", "RIGHT TOP" -> {
                x = (int) (img.getWidth() - textLayout.getBounds().getWidth() - textLayout.getBounds().getX());
                y = (int) textLayout.getBounds().getHeight();
            }
            case "LEFT" -> {
                x = (int)(0-textLayout.getBounds().getX());
                y = (int)(img.getHeight()/2  + textLayout.getBounds().getHeight()/2);
            }
            case "CENTER" -> {
                x = (int)(img.getWidth()/2-textLayout.getBounds().getWidth()/2-textLayout.getBounds().getX());
                y = (int)(img.getHeight()/2  + textLayout.getBounds().getHeight()/2);
            }
            case "RIGHT" -> {
                x = (int)(img.getWidth()-textLayout.getBounds().getWidth()-textLayout.getBounds().getX());
                y = (int)(img.getHeight()/2  + textLayout.getBounds().getHeight()/2);
            }
            case "LEFT BOTTOM", "BOTTOM LEFT" -> {
                x = (int)(0-textLayout.getBounds().getX());
                y = (img.getHeight());
            }
            case "BOTTOM" -> {
                x = (int)(img.getWidth()/2-textLayout.getBounds().getWidth()/2-textLayout.getBounds().getX());
                y = (img.getHeight());
            }
            case "BOTTOM RIGHT", "RIGHT BOTTOM" ->{
                x = (int)(img.getWidth()-textLayout.getBounds().getWidth()-textLayout.getBounds().getX());
                y = img.getHeight();
            }
            default  -> {
                System.out.println("Неправильно указана позиция. Будет использована позиция по умолчанию");
                x = (int)(img.getWidth()/2-textLayout.getBounds().getWidth()/2-textLayout.getBounds().getX());
                y = (int)(img.getHeight()/2  + textLayout.getBounds().getHeight()/2);
                saveMem(g, img,path, mem, x, y);
            }
        }


        saveMem(g, img,path, mem, x, y);
    }


    private void saveMem(Graphics2D g, BufferedImage img,String path, String mem, int x, int y) {
        String oldName = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."));
        String formatName = path.substring(path.lastIndexOf(".")+1);
        g.drawString(mem, x, y);
        g.dispose();
        try {
            ImageIO.write(img, formatName, new File(oldName+"New."+formatName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
