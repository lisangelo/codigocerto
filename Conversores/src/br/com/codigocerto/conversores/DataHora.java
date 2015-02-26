/*
 * DataHora.java
 *
 * Criado em 2 de Maio de 2007, 16:55
 *
 */

package br.com.codigocerto.conversores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lis
 */
public class DataHora {
    
    private final String FORMATO_DATA = "dd/MM/yy";
    private final String FORMATO_DATA_SECULO = "dd/MM/yyyy";
    private final String FORMATO_DATA_CURTA = "ddMMyy";
    private Date _data = null;
    
    /** Cria uma nova instância de DataHora */
    public DataHora() {
    }
    
    public void setData(java.util.Date data) {
        if (data != null) {
            _data = data;
        }
    }

    /***
     * Converte o texto em data
     * @param texto - dd/mm/yyyy
     * @return data
     */
    public Date setData(String texto) {
        if (texto != null) {

            SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("d/M/y");
            try {
                _data = objSimpleDateFormat.parse(texto);
            } catch (ParseException ex) {
                Logger.getLogger(DataHora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _data;
    }

    /***
     * Converte o texto da nfe em data
     * @param texto - yyyy-mm-dd
     * @return data
     */
    public Date setDataNFe(String texto) {
        if (texto != null) {

            SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("y-M-d");
            try {
                _data = objSimpleDateFormat.parse(texto);
            } catch (ParseException ex) {
                Logger.getLogger(DataHora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _data;
    }

    public String getDataFormatada() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATO_DATA);
        return formatter.format(_data);     
    }

    public String getDataFormatadaSeculo() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATO_DATA_SECULO);
        return formatter.format(_data);     
    }

    public String getDataFormatadaCurta() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATO_DATA_CURTA);
        return formatter.format(_data);     
    }

    public String getData() {
        java.sql.Date dataFormatada = new java.sql.Date(_data.getTime());
        return dataFormatada.toString();
    }
    
    
    public String getHora() {
        Time hora = new Time(_data.getTime());
        return hora.toString();
    }
    
    public Date addDias(int numero) {
        if (_data != null) {
            Calendar cal = new GregorianCalendar();
            try {
                cal.setTime(_data);
                cal.add(Calendar.DATE, numero);
                _data = cal.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return _data;
    }
    
    public int diferenca(Date segundaData) {
        int diferenca = 0;
        if (_data != null) {
            Calendar calPrimeiraData = new GregorianCalendar();
            Calendar calSegundaData = new GregorianCalendar();
            try {
                calPrimeiraData.setTime(_data);
                calSegundaData.setTime(segundaData);
                long difMilis = calSegundaData.getTimeInMillis() - calPrimeiraData.getTimeInMillis();
                diferenca = (int) (difMilis / (24 * 60 * 60 * 1000)); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return diferenca;
    }
    
    /**
     * Informa o dia da semana
     * @return int indicando o dia 0-Dom, 7-Sab
     */
    public int getDiaSemana() {
        Calendar cal = Calendar.getInstance();  
        cal.setTime(_data);
        int dia = cal.get(Calendar.DAY_OF_WEEK);
        return dia - 1;
    }
    
    /**
     * Informa o nome do dia da semana
     * @return nome
     */
    public String getDiaSemanaNome() {
        final String[] diasSemana = 
                new String[] {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};
        return diasSemana[getDiaSemana()];
    }
    
} ///:~
