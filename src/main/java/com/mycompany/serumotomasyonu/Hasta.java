/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serumotomasyonu;

/**
 *
 * @author yusufozturk
 */

public class Hasta {            //Hasta Entity
                                //Tüm işlemler buradaki 8 özellik ile yapılır.
    private int id;
    private String ad;
    private String soyad;
    private String tc;
    private String kan;
    private String sikayet;
    private String oda;
    private String serum;
    
//Constructorlar
    public Hasta(int id) {
    this.id=id;
        }          
    
    

    public Hasta(int id, String ad, String soyad, String tc, String kan, String sikayet, String oda, String serum) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.tc = tc;
        this.kan = kan;
        this.sikayet = sikayet;
        this.oda = oda;
        this.serum = serum;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getKan() {
        return kan;
    }

    public void setKan(String kan) {
        this.kan = kan;
    }

    public String getSikayet() {
        return sikayet;
    }

    public void setSikayet(String sikayet) {
        this.sikayet = sikayet;
    }

    public String getOda() {
        return oda;
    }

    public void setOda(String oda) {
        this.oda = oda;
    }

    public String getSerum() {
        return serum;
    }

    public void setSerum(String serum) {
        this.serum = serum;
    }
    
    
}
