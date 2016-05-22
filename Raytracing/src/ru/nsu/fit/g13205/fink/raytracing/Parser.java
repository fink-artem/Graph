package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    static Data parse(File file) throws Exception {
        Data data = new Data();
        int number;
        double x, y, z;
        int search;
        try (Scanner reader = new Scanner(file)) {
            String s = getNextLine(reader);
            data.setaRGB(readColor(s));
            s = getNextLine(reader).trim();
            number = Integer.parseInt(s);
            if (number < 0) {
                throw new Exception();
            }
            List<Source> sourceList = new ArrayList<>();
            for (int i = 0; i < number; i++) {
                s = getNextLine(reader);
                search = s.indexOf(" ");
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
                search = s.indexOf(" ");
                if (search != -1) {
                    z = Double.parseDouble(s.substring(0, search));
                    s = s.substring(search + 1).trim();
                } else {
                    throw new Exception();
                }
                sourceList.add(new Source(x, y, z, readColor(s)));
            }
            data.setSourceList(sourceList);
            List<Shape> shapeList = new ArrayList<>();
            Shape shape;
            while (true) {
                shape = readShape(reader);
                if (shape == null) {
                    break;
                } else {
                    shapeList.add(shape);
                }
            }
            data.setShapeList(shapeList);
        }
        try {
            RenderData renderData = ParserRender.parse(new File(file.toString().substring(0, file.toString().length()-6) + ".render"));
            data.setRenderData(renderData);
        } catch (Exception e) {
        }
        return data;
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

    private static Shape readShape(Scanner reader) throws Exception {
        String s = getNextLine(reader);
        if (s == null) {
            return null;
        }
        switch (s.toUpperCase()) {
            case "SPHERE":
                return readSphere(reader);
            case "BOX":
                return readBox(reader);
            case "TRIANGLE":
                return readTriangle(reader);
            case "QUADRANGLE":
                return readQuadrangle(reader);
            default:
                throw new Exception();
        }
    }

    private static Sphere readSphere(Scanner reader) throws Exception {
        String s = getNextLine(reader);
        Coordinate3D center = readCoordinate(s);
        s = getNextLine(reader);
        double radius = Double.parseDouble(s);
        double kdr, kdg, kdb, ksr, ksg, ksb;
        double power;
        s = getNextLine(reader);
        int search = s.indexOf(" ");
        if (search != -1) {
            kdr = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            kdg = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            kdb = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksr = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksg = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksb = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        power = Double.parseDouble(s);
        return new Sphere(center, radius, kdr, kdg, kdb, ksr, ksg, ksb, power);
    }

    private static Box readBox(Scanner reader) throws Exception {
        String s = getNextLine(reader);
        Coordinate3D min = readCoordinate(s);
        s = getNextLine(reader);
        Coordinate3D max = readCoordinate(s);
        double kdr, kdg, kdb, ksr, ksg, ksb;
        double power;
        s = getNextLine(reader);
        int search = s.indexOf(" ");
        if (search != -1) {
            kdr = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            kdg = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            kdb = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksr = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksg = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksb = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        power = Double.parseDouble(s);
        return new Box(min, max, kdr, kdg, kdb, ksr, ksg, ksb, power);
    }

    private static Triangle readTriangle(Scanner reader) throws Exception {
        String s = getNextLine(reader);
        Coordinate3D point1 = readCoordinate(s);
        s = getNextLine(reader);
        Coordinate3D point2 = readCoordinate(s);
        s = getNextLine(reader);
        Coordinate3D point3 = readCoordinate(s);
        double kdr, kdg, kdb, ksr, ksg, ksb;
        double power;
        s = getNextLine(reader);
        int search = s.indexOf(" ");
        if (search != -1) {
            kdr = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            kdg = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            kdb = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksr = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksg = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksb = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        power = Double.parseDouble(s);
        return new Triangle(point1, point2, point3, kdr, kdg, kdb, ksr, ksg, ksb, power);
    }

    private static Quadrangle readQuadrangle(Scanner reader) throws Exception {
        String s = getNextLine(reader);
        Coordinate3D point1 = readCoordinate(s);
        s = getNextLine(reader);
        Coordinate3D point2 = readCoordinate(s);
        s = getNextLine(reader);
        Coordinate3D point3 = readCoordinate(s);
        s = getNextLine(reader);
        Coordinate3D point4 = readCoordinate(s);
        double kdr, kdg, kdb, ksr, ksg, ksb;
        double power;
        s = getNextLine(reader);
        int search = s.indexOf(" ");
        if (search != -1) {
            kdr = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            kdg = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            kdb = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksr = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksg = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            ksb = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        power = Double.parseDouble(s);
        return new Quadrangle(point1, point2, point3, point4, kdr, kdg, kdb, ksr, ksg, ksb, power);
    }
}
