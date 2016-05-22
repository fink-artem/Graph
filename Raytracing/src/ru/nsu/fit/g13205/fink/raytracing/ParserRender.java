package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;
import java.io.File;
import java.util.Scanner;

public class ParserRender {

    static RenderData parse(File file) throws Exception {
        RenderData renderData = new RenderData();
        try (Scanner reader = new Scanner(file)) {
            renderData.setBackGround(readColor(getNextLine(reader)));
            renderData.gamma = Double.parseDouble(getNextLine(reader));
            renderData.depth = Integer.parseInt(getNextLine(reader));
            String s = getNextLine(reader).toUpperCase();
            switch (s) {
                case "ROUGH":
                    renderData.quality = Quality.ROUGH;
                    break;
                case "NORMAL":
                    renderData.quality = Quality.NORMAL;
                    break;
                case "FINE":
                    renderData.quality = Quality.FINE;
                    break;
                default:
                    throw new Exception();
            }
            renderData.eye = readCoordinate(getNextLine(reader));
            renderData.ref = readCoordinate(getNextLine(reader));
            renderData.upVector = readCoordinate(getNextLine(reader));
            s = getNextLine(reader);
            int search = s.indexOf(" ");
            if (search != -1) {
                renderData.zn = Double.parseDouble(s.substring(0, search));
                s = s.substring(search + 1).trim();
            }else{
                throw new Exception();
            }
            renderData.zf = Double.parseDouble(s);
            s = getNextLine(reader);
            search = s.indexOf(" ");
            if (search != -1) {
                renderData.sw = Double.parseDouble(s.substring(0, search));
                s = s.substring(search + 1).trim();
            }else{
                throw new Exception();
            }
            renderData.sh = Double.parseDouble(s);
        }
        return renderData;
    }

    private static String getNextLine(Scanner reader) {
        String s;
        do {
            if (!reader.hasNext()) {
                return null;
            }
            s = reader.nextLine();
            int search = s.indexOf("//");
            if (search != - 1) {
                s = s.substring(0, search).trim();
            }
        } while (s.isEmpty());
        return s;
    }

    private static Color readColor(String s) throws Exception {
        int search = s.indexOf(" ");
        int r, g, b;
        if (search != -1) {
            r = Integer.parseInt(s.substring(0, search));
            if (r < 0 || r > 255) {
                throw new Exception();
            }
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            g = Integer.parseInt(s.substring(0, search));
            if (g < 0 || g > 255) {
                throw new Exception();
            }
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        b = Integer.parseInt(s);
        if (b < 0 || b > 255) {
            throw new Exception();
        }
        return new Color(r, g, b);
    }

    private static Coordinate3D readCoordinate(String s) throws Exception {
        int search = s.indexOf(" ");
        double x, y, z;
        if (search != -1) {
            x = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            y = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        z = Double.parseDouble(s);
        return new Coordinate3D(x, y, z);
    }
}
