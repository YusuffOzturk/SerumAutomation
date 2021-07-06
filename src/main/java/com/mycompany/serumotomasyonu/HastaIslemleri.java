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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class HastaIslemleri {
    private Connection con = null;
    
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    
    
    public ArrayList<Hasta> hastalariGetir(){
    ArrayList<Hasta> cikti = new ArrayList<Hasta>();

        try {
            statement=con.createStatement();
            String sorgu = "Select*From hasta";
            ResultSet rs=statement.executeQuery(sorgu);
            
            while(rs.next()){            
                int id=rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String tc = rs.getString("tc");
                String kan = rs.getString("kan");
                String sikayet = rs.getString("sikayet");
                String oda = rs.getString("oda");
                String serum = rs.getString("serum");
                cikti.add(new Hasta(id,ad,soyad,tc,kan,sikayet,oda,serum));
                
                
            }
            return cikti;
        } catch (SQLException ex) {
            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public Hasta callbyId(int id){
        try {
            Hasta hasta = new Hasta(id);
            String sorgu = "Select * From hasta where id = ?";
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                String ad=rs.getString("ad");
                String soyad = rs.getString("soyad");
                String tc = rs.getString("tc");
                String kan = rs.getString("kan");
                String sikayet = rs.getString("sikayet");
                String oda = rs.getString("oda");
                String serum = rs.getString("serum");
               
                hasta.setId(id);
                hasta.setAd(ad);
                hasta.setSoyad(soyad);
                hasta.setTc(tc);
                hasta.setKan(kan);
                hasta.setSikayet(sikayet);
                hasta.setOda(oda);
                hasta.setSerum(serum);
              
            }
                        return hasta;

        } catch (SQLException ex) {
            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
      
       

    }
    
    public void hastaDuzenle (int id,String yeni_ad,String yeni_soyad,String yeni_tc,String yeni_kan,String yeni_sikayet,String yeni_oda){
        String sorgu = "Update hasta set ad = ? , soyad = ? , tc = ? , kan = ? , sikayet = ? , oda = ? where id = ?";

        try {
            preparedStatement=con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, yeni_ad);
            preparedStatement.setString(2, yeni_soyad);
            preparedStatement.setString(3, yeni_tc);
            preparedStatement.setString(4, yeni_kan);
            preparedStatement.setString(5, yeni_sikayet);
            preparedStatement.setString(6, yeni_oda);
            
            preparedStatement.setInt(7, id);
            
            preparedStatement.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public void serumEkle(int id){
        String sorgu ="Update hasta set serum = ? where id = ?";

        try{
        
        SerialPort serialPort=new SerialPort("/dev/tty.usbserial-1410");
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
             serialPort.addEventListener(new SerialPortEventListener(){
            @Override
                public void serialEvent(SerialPortEvent event){
                if(event.isRXCHAR()&& event.getEventValue()>0){
                    try {
                        String data=serialPort.readString(event.getEventValue());
                        String serum=data.toString();
                        try {
                            preparedStatement=con.prepareStatement(sorgu);
                        } catch (SQLException ex) {
                            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            preparedStatement.setString(1, serum);
                        } catch (SQLException ex) {
                            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            preparedStatement.setInt(2, id);
                        } catch (SQLException ex) {
                            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            preparedStatement.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                        }




                        
                    } catch (SerialPortException ex) {
                        Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                    }
  }

            }   
        });
        }  
         catch (SerialPortException ex) {
            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
                                            
                                           
                                                //System.out.println(data);
                                                //serumObj.setSerum(data);
                                                //preparedStatement=con.prepareStatement(sorgu);
                                            
                                                //preparedStatement.setString(1, data);
                                            
                                                //preparedStatement.setInt(2, id);
                                           
    }
        
        
    
    public boolean girisYap(String kullanici_adi, String parola){
        
        try {
            String sorgu ="Select*From admin where username=? and password=?";
            
                preparedStatement = con.prepareStatement(sorgu);           
                preparedStatement.setString(1,kullanici_adi);
                preparedStatement.setString(2,parola);
            
            ResultSet rs =preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public HastaIslemleri(){
    String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_ismi+ "?useUnicode=true&characterEncoding=utf8";    
    try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı....");
        }
        
        
        try {
            con = DriverManager.getConnection(url, Database.kullanici_adi, Database.parola);
            System.out.println("Bağlantı Başarılı...");
            
            
        } catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız...");
            //ex.printStackTrace();
        }}
    // SERUM EKLENEBİLİR
    public void hastaEkle(String ad,String soyad,String tc,String kan,String sikayet,String oda, String serum){
                    String sorgu = "Insert Into hasta (ad,soyad, tc, kan,sikayet, oda,serum) VALUES(?,?,?,?,?,?,?)" ;

        try {
            preparedStatement = con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, tc);
            preparedStatement.setString(4, kan);
            preparedStatement.setString(5, sikayet);
            preparedStatement.setString(6, oda);
            preparedStatement.setString(7, serum);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void hastaSil(int id){
        String sorgu="Delete from hasta where id = ?";
        try {
            
            preparedStatement= con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HastaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public static void main(String args[]){
    HastaIslemleri islemler = new HastaIslemleri();
    }

}

