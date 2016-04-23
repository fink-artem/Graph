package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    static Data parse(File file) throws Exception {
        Data data = new Data();
        try (Scanner reader = new Scanner(file)) {
            String s = getNextLine(reader);
            int search = s.indexOf(" ");
            int number;
            double d;
            if (search != -1) {
                number = Integer.parseInt(s.substring(0, search));
                if (number < 1 || number > 50) {
                    throw new Exception();
                }
                data.setN(number);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            search = s.indexOf(" ");
            if (search != -1) {
                number = Integer.parseInt(s.substring(0, search));
                if (number < 1 || number > 50) {
                    throw new Exception();
                }
                data.setM(number);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            search = s.indexOf(" ");
            if (search != -1) {
                number = Integer.parseInt(s.substring(0, search));
                if (number < 1 || number > 50) {
                    throw new Exception();
                }
                data.setK(number);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            search = s.indexOf(" ");
            if (search != -1) {
                d = Double.parseDouble(s.substring(0, search));
                if (d < 0 || d > 1) {
                    throw new Exception();
                }
                data.setA(d);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            search = s.indexOf(" ");
            if (search != -1) {
                d = Double.parseDouble(s.substring(0, search));
                if (d < 0 || d > 1 || d < data.getA()) {
                    throw new Exception();
                }
                data.setB(d);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            search = s.indexOf(" ");
            if (search != -1) {
                d = Double.parseDouble(s.substring(0, search));
                if (d < 0 || d > Math.PI * 2) {
                    throw new Exception();
                }
                data.setC(d);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            d = Double.parseDouble(s);
            if (d < 0 || d > Math.PI * 2 || d < data.getC()) {
                throw new Exception();
            }
            data.setD(d);

            s = getNextLine(reader);
            search = s.indexOf(" ");
            if (search != -1) {
                d = Double.parseDouble(s.substring(0, search));
                if (d < 0) {
                    throw new Exception();
                }
                data.setZn(d);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            search = s.indexOf(" ");
            if (search != -1) {
                d = Double.parseDouble(s.substring(0, search));
                if (d < 0) {
                    throw new Exception();
                }
                data.setZf(d);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            search = s.indexOf(" ");
            if (search != -1) {
                d = Double.parseDouble(s.substring(0, search));
                if (d < 0) {
                    throw new Exception();
                }
                data.setSw(d);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            d = Double.parseDouble(s);
            if (d < 0) {
                throw new Exception();
            }
            data.setSh(d);

            data.setRotateMatrix(getMatrix(reader));

            s = getNextLine(reader);
            search = s.indexOf(" ");
            if (search != -1) {
                number = Integer.parseInt(s.substring(0, search));
                if (number < 0 || number > 255) {
                    throw new Exception();
                }
                data.setBr(number);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            search = s.indexOf(" ");
            if (search != -1) {
                number = Integer.parseInt(s.substring(0, search));
                if (number < 0 || number > 255) {
                    throw new Exception();
                }
                data.setBg(number);
                s = s.substring(search + 1).trim();
            } else {
                throw new Exception();
            }
            number = Integer.parseInt(s);
            if (number < 0 || number > 255) {
                throw new Exception();
            }
            data.setBb(number);

            s = getNextLine(reader);
            int k = Integer.parseInt(s);
            for (int i = 0; i < k; i++) {
                s = getNextLine(reader);
                int r, g, b;
                search = s.indexOf(" ");
                if (search != -1) {
                    r = Integer.parseInt(s.substring(0, search));
                    s = s.substring(search + 1).trim();
                } else {
                    throw new Exception();
                }
                search = s.indexOf(" ");
                if (search != -1) {
                    g = Integer.parseInt(s.substring(0, search));
                    s = s.substring(search + 1).trim();
                } else {
                    throw new Exception();
                }
                b = Integer.parseInt(s);
                s = getNextLine(reader);
                double cx, cy, cz;
                search = s.indexOf(" ");
                if (search != -1) {
                    cx = Double.parseDouble(s.substring(0, search));
                    s = s.substring(search + 1).trim();
                } else {
                    throw new Exception();
                }
                search = s.indexOf(" ");
                if (search != -1) {
                    cy = Double.parseDouble(s.substring(0, search));
                    s = s.substring(search + 1).trim();
                } else {
                    throw new Exception();
                }
                cz = Double.parseDouble(s);

                data.addNewModel(cx, cy, cz, getMatrix(reader), new Color(r, g, b));
                s = getNextLine(reader);
                int n = Integer.parseInt(s);
                List<Coordinate2D> pivotList = new ArrayList<>();
                double x, y;
                for (int j = 0; j < n; j++) {
                    s = getNextLine(reader);
                    search = s.indexOf(" ");
                    if (search != -1) {
                        x = Double.parseDouble(s.substring(0, search));
                        s = s.substring(search + 1).trim();
                    } else {
                        throw new Exception();
                    }
                    y = Double.parseDouble(s);
                    pivotList.add(new Coordinate2D(x, y));
                }
                data.setPivotsListInModel(i, pivotList);
            }
        } 
        return data;
    }

    private static String getNextLine(Scanner reader) {
        String s;
        do {
            s = reader.nextLine();
            int search = s.indexOf("//");
            if (search != - 1) {
                s = s.substring(0, search).trim();
            }
        } while (s.isEmpty());
        return s;
    }

    private static double[][] getMatrix(Scanner reader) throws Exception {
        double[][] matrix = new double[4][4];
        String s = getNextLine(reader);
        int search = s.indexOf(" ");
        if (search != -1) {
            matrix[0][0] = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            matrix[0][1] = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        matrix[0][2] = Double.parseDouble(s);

        s = getNextLine(reader);
        search = s.indexOf(" ");
        if (search != -1) {
            matrix[1][0] = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            matrix[1][1] = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        matrix[1][2] = Double.parseDouble(s);

        s = getNextLine(reader);
        search = s.indexOf(" ");
        if (search != -1) {
            matrix[2][0] = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        search = s.indexOf(" ");
        if (search != -1) {
            matrix[2][1] = Double.parseDouble(s.substring(0, search));
            s = s.substring(search + 1).trim();
        } else {
            throw new Exception();
        }
        matrix[2][2] = Double.parseDouble(s);
        matrix[3][3] = 1;
        return matrix;
    }

}
