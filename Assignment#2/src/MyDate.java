import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MyDate implements Cloneable, Comparable<MyDate>, Serializable {
    private int year;
    private int month;
    private int day;

    MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String toString() {
        return year + "/" + month + "/" + day;
    }

    public void print() {
        System.out.print(" " + year + "/" + month + "/" + day + " ");
    }

    public Boolean isExpired(MyDate expiryDate) {
        if (year < expiryDate.year) {
            return false;
        } else if (year == expiryDate.year) {
            if (month < expiryDate.month) {
                return false;
            } else if (month == expiryDate.month) {
                if (day < expiryDate.day) {
                    return false;
                }
            }
        }
        return true;
    }

    //lab4
    public MyDate(MyDate myDate) {
        this.day = myDate.day;
        this.month = myDate.month;
        this.year = myDate.year;
    }

    @Override
    public MyDate clone() throws CloneNotSupportedException {
        return (MyDate) super.clone();
    }

    public int compareTo(MyDate otherDate) {
        if (otherDate.year < year) {
            return 1;
        } else if (otherDate.year == year && otherDate.month < month) {
            return 1;
        } else if (otherDate.year == year && otherDate.month == month && otherDate.day < day) {
            return 1;
        } else if (otherDate.year == year && otherDate.month == month && otherDate.day == day) {
            return 0;
        } else {
            return -1;
        }
    }

    //Lab6
    public String toDelimitedString() {
        return year + "," + month + "," + day;
    }
}
