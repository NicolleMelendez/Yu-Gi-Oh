/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sena.edu.co.yugioh;

/**
 *
 * @author Aprendiz
 */
public class CardGame {
    private int id;
    private String name;
    private int atk;
    private int def;
    private String img;

    public CardGame() {
    }

    public CardGame(int id, String name, int atk, int def, String img) {
        this.id = id;
        this.name = name;
        this.atk = atk;
        this.def = def;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
    
    
}
