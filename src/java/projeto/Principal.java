package projeto;

//Importações
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

//importações para salvar pdf
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

@SessionScoped
@ManagedBean(name="principal")
public class Principal implements Serializable{

    private String txt = "", txt1 = "", resultado="", resultado2="<br /><br /><br />", resultado3="", resultado4="";
    private String control="" ;
    private int tam=0, tamp=0, tampmax=0, op=3;
    private boolean tab=false;  
    private boolean quebra=false;
    private UploadedFile file;
    
    //Getters e Setters
    
    public UploadedFile getFile() {
        return file;
    }
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public String getTxt() {
        return txt;
    }
    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }
    
    

    public int getTam() {
        return tam;
    }
    public void setTam(int tam) {
        this.tam = tam;
    }

    public int getTamp() {
        return tamp;
    }
    public void setTamp(int tamp) {
        this.tamp = tamp;
    }

    public int getTampmax() {
        return tampmax;
    }
    public void setTampmax(int tampmax) {
        this.tampmax = tampmax;
    }

    public int getOp() {
        return op;
    }
    public void setOp(int op) {
        this.op = op;
    }

    public String getControl() {
        return control;
    }
    public void setControl(String control) {
        this.control = control;
    }
    
    public String getResultado() {
        return resultado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public String getResultado2() {
        return resultado2;
    }
    public void setResultado2(String resultado2) {
        this.resultado2 = resultado2;
    }

    public String getResultado3() {
        return resultado3;
    }
    public void setResultado3(String resultado3) {
        this.resultado3 = resultado3;
    }

    public String getResultado4() {
        return resultado4;
    }
    public void setResultado4(String resultado4) {
        this.resultado4 = resultado4;
    }

    public boolean isTab() {
        return tab;
    }
    public void setTab(boolean tab) {
        this.tab = tab;
    }

    public boolean isQuebra() {
        return quebra;
    }
    public void setQuebra(boolean quebra) {
        this.quebra = quebra;
    }
        //método para upload de arquivos
        public void upload(FileUploadEvent event) throws IOException{
		this.file = event.getFile();
		
		this.txt = new Scanner(file.getInputstream(),"UTF-8").useDelimiter("\\A").next();
		          //System.out.println("texto = " + txt);
		
	}
        //Método para chamar os outros métodos da aplicação 
        public void chamaMetodos() throws FileNotFoundException{ 
            int aux = txt.length();
            System.out.println("texto2 = " + this.txt);
            if(txt.charAt(aux-1) == '.'){ //se o texto terminar com um ponto (.)
                
                dadosPeriodo(txt, tam);
                dadosParagrafo(txt, tamp, tampmax, op);
                sugestoes(txt);
                conjConclusivas(txt, op);
                colocacaoSujeito(txt, op);
                
            }else{
                control="";
                resultado3 = "Verifique a pontuação do seu texto! É necessário um ponto final(.) .";
            }
        }
        
        //String quebraLinha = System.lineSeparator();
        
        //método que verifica se os PERÍODOS possuem a quantidade de caracteres desejada pelo usuário
	public void dadosPeriodo(String txt, int tam){
		
		int i=0, contp=0, contc=0, contpar=1, conta =1, contm=0, contat=0, contpe=0;
		resultado="";
                resultado2="";
                resultado3="";
                resultado4="";
                control = "";
                
                
		ArrayList<String> dadosm = new ArrayList<String>();
		ArrayList<String> periodo = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(txt, ".");
		
		while (st.hasMoreTokens()){
			periodo.add(st.nextToken());
			contpe++;
		}
		
		while(conta <= txt.length()){
			if(txt.charAt(i) != '.' && txt.charAt(i) != '\n'){
				contc++;
			}
			/*
                        if(txt.charAt(i) == '\n' && contc==0){
                            
                        }*/
                        
			if(txt.charAt(i) == '.'){
				//contp++; //incrementa a quantidade de palavras
				contc++; //conta a quantidade de caracteres do período
				
                            
				if(contc > tam){ //verifica se a quantidade de caracteres é maior que a desejada

					dadosm.add(periodo.get(contpar-1) + ". CARACTERES: " + contc);
					
					contm++; //conta a quantidade de periodos que ultrapassaram a quantidade de caracteres
					
				}
                            
				contpar++; //conta a quantidade de parágrafos
				contat++; //conta a quantidade de periodos
				
				contc=0;
				contp=0;
			}
			
			/*else if(txt.charAt(i) == ' '){ //conta a quantidade de palavras
				if(txt.charAt(i-1) != '.'){
					contp++;
				}
			}*/
			
			i++;
			conta++; //conta a quantidade de caracteres no total
		}
		
		//JOptionPane.showMessageDialog(null, dadosm, "Períodos que ultrapassam " +tam + "caracteres", WIDTH);
		
		if(contm > 0){
                    //System.out.println("\nPERÍODOS QUE ULTRAPASSAM: " + tam + " CARACTERES.");
                    //resultado = resultado + "PERÍODOS QUE ULTRAPASSAM " + tam + " CARACTERES:<br />";
                    resultado4 = resultado4 + "PERÍODOS QUE ULTRAPASSAM " + tam + " CARACTERES:\n\n";
                    System.out.println(tam);
                    //control ="";
                    //Imprime ArrayList com dados dos períodos que ultrapassam o limite desejado
                    for(int j = 0; j< contm; j++){
                            //System.out.println(dadosm.get(j));
                            //resultado = resultado + dadosm.get(j) + "<br /> <br />";
                            resultado4 = "\t"+ resultado4 + dadosm.get(j) + "\n";

                            //quebraLinha

                    }
                }
                /*
                else{
                    control="Parabéns! O \"NOME DO PROJETO AQUI\" não identificou nenhum erro em seu texto." ;
                }
                */
                
	}
	
        //método que verifica se os PARÁGRAFOS possuem a quantidade de caracteres desejada pelo usuário
	public void dadosParagrafo(String txt, int tamp, int tampmax, int op){
		int i=0,  contc=0, contpar=1, conta=1, contm=0, contat=0, contpe=0, contp=0, contmen =0;
		String aux=null;
		
		ArrayList<String> dadosm = new ArrayList<String>();
		ArrayList<String> dadosmen = new ArrayList<String>();
		ArrayList<String> paragrafo = new ArrayList<String>();
		
		switch(op){
		
		
			case 1:
				/*StringTokenizer st = new StringTokenizer(txt, "\t");
				
				while (st.hasMoreTokens()){
					paragrafo.add(st.nextToken());
					contpe++;
				}*/
				
				for(int j = 0; j< txt.length(); j++){
					if(txt.charAt(j) == '\t'){ 
						contpe++;
					}
				}
				
				String array[] = new String[contpe];
				//for(int j = 0; j< txt.length(); j++){
					array = txt.split("\t");
					
				//}
				for(int j = 1; j<= contpe; j++){
                                    //if(!"\n".equals(array[j])){
					aux = (String) array[j];
					paragrafo.add(aux);
                                    //}
                                    
				}
				
				
				while(conta <= txt.length()){
					if(txt.charAt(i) != '\t'){
						contc++;
					}
					
					if((txt.charAt(i) == '\t' || i+1 == txt.length()) && i != 0){
						//contp++; //incrementa a quantidade de palavras
						 
						if((i+1) == txt.length()){
							contc++;
						}else{
							contc--;
						} 
                                                
                                                //System.out.println("AQUIIIIIIIIIII" + paragrafo.get(contpar-1));
						
                                                    if(contc > tampmax){ //verifica se a quantidade de caracteres é maior que a desejada
                                                            dadosm.add("\n" + paragrafo.get(contpar-1) + "\nCARACTERES: " + contc);
                                                            contm++;		
                                                    }else if(contc < tamp){ //verifica se a quantidade de caracteres é menor que a desejada
                                                            dadosmen.add("\n" + paragrafo.get(contpar-1) + "\nCARACTERES: " + contc);
                                                            contmen++;

                                                    }
                                                
						contpar++;
						contat++;
						
						contc=0;
						contp=0;
					}
					
					/*else if(txt.charAt(i) == ' '){ //conta a quantidade de palavras
						if(txt.charAt(i-1) != '.'){
							contp++;
						}
						
					}*/
					i++;
					conta++;
				}
				
			;break;
			
			case 2:
				
				StringTokenizer st2 = new StringTokenizer(txt, "\n");
				
				while (st2.hasMoreTokens()){
					paragrafo.add(st2.nextToken());
					contpe++;
				}
				
				while(conta <= txt.length()){
					if(txt.charAt(i) != '\n'){
						contc++;
					}
					
					if(txt.charAt(i) == '\n' || i+1 == txt.length()){
						//contp++; //incrementa a quantidade de palavras
						
						if(contc > tampmax){ //verifica se a quantidade de caracteres é maior que a desejada
							dadosm.add("\n" + paragrafo.get(contpar-1) + "\nCARACTERES: " + contc);
							contm++;		
						}else if(contc < tamp){ //verifica se a quantidade de caracteres é menor que a desejada
							dadosmen.add("\n" + paragrafo.get(contpar-1) + "\nCARACTERES: " + contc);
							contmen++;
							
						}
						
						contpar++;
						contat++;
						
						contc=0;
						contp=0;
					}
					
					/*else if(txt.charAt(i) == ' '){ //conta a quantidade de palavras
						if(txt.charAt(i-1) != '.'){
							contp++;
						}
						
					}*/
					i++;
					conta++;
				}
			;break;
			
			case 3: 
				int contt=0;
				
				for(int j = 0; j< txt.length(); j++){
					if(txt.charAt(j) == '\n' && txt.charAt(j+1) == '\t'){
						contt++;
					}
				}
				
				
				String array1[] = new String[contt];
				//for(int j = 0; j< txt.length(); j++){
					array1 = txt.split("\n\t");
				//}
				for(int j = 0; j<= contt; j++){
					aux = (String) array1[j];
					paragrafo.add(aux);
					
				}
				
				while(conta <= txt.length()){
					if(txt.charAt(i) != '\n'){
						contc++;
					}
					
					if((txt.charAt(i) == '\n' && txt.charAt(i+1) == '\t') || i+1 == txt.length() && i != 0){
						//contp++; //incrementa a quantidade de palavras
						
						if(contc > tampmax){ //verifica se a quantidade de caracteres é maior que a desejada
							dadosm.add("\n" + paragrafo.get(contpar-1) + "\nCARACTERES: " + contc);
							contm++;		
						}
                                                if(contc < tamp){ //verifica se a quantidade de caracteres é menor que a desejada
							dadosmen.add("\n" + paragrafo.get(contpar-1) + "\nCARACTERES: " + contc);
							contmen++;
							
						}
						
						contpar++;
						contat++;
						
						contc=0;
						contp=0;
					}
					
					/*else if(txt.charAt(i) == ' '){ //conta a quantidade de palavras
						if(txt.charAt(i-1) != '.'){
							contp++;
						}
						
					}*/
					i++;
					conta++;
				}
			;break;
			
		}
		
		//JOptionPane.showMessageDialog(null, dadosm, "Períodos que ultrapassam " +tamp + "caracteres", WIDTH);
		if(contm > 0){
                    //resultado = resultado + "<br />PARÁGRAFOS QUE ULTRAPASSAM " + tampmax + " CARACTERES: <br />";
                    resultado4 = resultado4 + "\n\nPARÁGRAFOS QUE ULTRAPASSAM " + tampmax + " CARACTERES: \n";
                    //control ="";
                    //System.out.println("\nPARÁGRAFOS QUE ULTRAPASSAM " + tampmax + " CARACTERES.");
                    //Imprime ArrayList com dados dos períodos que ultrapassam o limite desejado
                    for(int j = 0; j< contm; j++){
                        //resultado = resultado + dadosm.get(j) + "<br /><br />";
                        resultado4 = resultado4 + dadosm.get(j) + "\n";
                            //System.out.println(dadosm.get(j));
                    }
                    
                }
                        //System.out.println("\nPARÁGRAFOS QUE POSSUEM MENOS DE " + tamp + " CARACTERES.");
                if(contmen > 0){
                    //resultado = resultado + "<br />PARÁGRAFOS QUE POSSUEM MENOS DE " + tamp + " CARACTERES: <br />";
                    resultado4 = resultado4 + "\n\nPARÁGRAFOS QUE POSSUEM MENOS DE " + tamp + " CARACTERES: \n";
                    //control ="";
                        for(int j = 0; j< contmen; j++){
                            //resultado = resultado + dadosmen.get(j) + "<br /><br />";
                            resultado4 = resultado4 + dadosm.get(j) + "\n";
                                //System.out.println(dadosmen.get(j));
                        }
                }
                /*
                if(contm <=0 && contmen <=0){
                    control="Parabéns! O \"NOME DO PROJETO AQUI\" não identificou nenhum erro em seu texto." ;
                }*/
	
	}
	
        //método que sugere evitar uso de determinados qualificadores
	public void sugestoes(String txt){
		int contpala=0, contpe=0, contc=0, conta=0, contm=0, contpar=0, conts=0;
		
		ArrayList<String> qualif = new ArrayList<String>();
		ArrayList<String> sugest = new ArrayList<String>();
		ArrayList<String> periodo = new ArrayList<String>();
		ArrayList<String> periodoC = new ArrayList<String>();
		ArrayList<String> periodoAux = new ArrayList<String>();
		ArrayList<String> periodoErro = new ArrayList<String>();

		
		//separa os períodos em uma ArrayList
		StringTokenizer st = new StringTokenizer(txt, ".");
		
		while (st.hasMoreTokens()){
			periodo.add(st.nextToken());
			contpe++;
		}
		
		//definição dos qualificadores
		qualif.add("grande");
		qualif.add("muito");
		qualif.add("muita");
		qualif.add("pouco");
		qualif.add("pouca");
		qualif.add("várias");
		qualif.add("vários");
		qualif.add("pequeno");
		qualif.add("pequena");
		qualif.add("enorme");
		qualif.add("sério");
		qualif.add("séria");
		qualif.add("bonita");
		qualif.add("bonito");
		qualif.add("minúsculo");
		qualif.add("excelente");
		qualif.add("gigante");
		qualif.add("incompetente");
		qualif.add("irresponsável");
		qualif.add("estranho");
		qualif.add("belo");/////////
		qualif.add("bela");////////////
		qualif.add("feliz");
		qualif.add("terrível");
		qualif.add("difícil");
		qualif.add("impossível");
		qualif.add("imenso");///////////
		qualif.add("imensa");/////////////
		qualif.add("evidente");////////
		qualif.add("horrível");
		qualif.add("lindo");
		qualif.add("linda");
		qualif.add("maravilhoso");////////////
		qualif.add("maravilhosa");//////////
		qualif.add("extraordinário");
		qualif.add("extraordinária");
		qualif.add("inteligente");
		qualif.add("incapaz");
		qualif.add("absurdo"); /////////////
		qualif.add("absoluto"); //////
		qualif.add("ridículo");
		qualif.add("óbvio");
		qualif.add("excelente"); ////
		qualif.add("infinito"); /////////
		qualif.add("infinita");
		qualif.add("esplêndido");
		qualif.add("esplêndida");
		qualif.add("surpreendente"); ///////
		qualif.add("razoável"); /////
		qualif.add("amplo");
		qualif.add("ampla");
		qualif.add("elevado"); //////
		qualif.add("elevada");
		qualif.add("incrível");
		qualif.add("incrivelmente");
		qualif.add("fantástico");
		qualif.add("insuportável");
		qualif.add("supremo");
		qualif.add("inevitável");
		qualif.add("gigantesco");
		qualif.add("gigantesca");
		qualif.add("bastante"); ////////
		qualif.add("grandioso");
		qualif.add("grandiosa");
		qualif.add("implacável");
		qualif.add("intolerável");
		qualif.add("indispensável");
		qualif.add("improvável");
		qualif.add("extravagante");
		qualif.add("suprema");
		qualif.add("inexplicável");
		qualif.add("inflexível");
		qualif.add("inacessível");
		qualif.add("impenetrável"); //////////
		qualif.add("impessável");
		qualif.add("infindável");
		qualif.add("gostoso");
		qualif.add("gostosa");
		qualif.add("inacessível");
		qualif.add("imperceptível");
		qualif.add("imprevisto");
		qualif.add("inesgotável");
		qualif.add("deslumbrante");
                qualif.add("tentador");
                qualif.add("provocador");
                qualif.add("perturbante");
                qualif.add("irresponsável");
                qualif.add("irremediável");
                qualif.add("inigualável");
                qualif.add("indestrutível");
                qualif.add("incontrolável");
                qualif.add("imperdoável");
                qualif.add("espetacular");
                qualif.add("adorável");
                qualif.add("maravilhado");
                qualif.add("indecifrável");
                qualif.add("incurável");
                qualif.add("imcomparável");
                qualif.add("incalculável");
                qualif.add("inacreditável");
                qualif.add("fabuloso");
                
                
		
		int contr=0, contr2=0, controle=0;
		
		//busca qualificadores em todos os períodos da ArrayList "periodo"
		for(int i = 0; i< contpe; i++){
			for(int j = 0; j< qualif.size(); j++){
				if(periodo.get(i).toLowerCase().contains(qualif.get(j).toLowerCase())){ //busca os qualificadores no ArrayList que contém todos os períodos.
					if(contr == 0){ //garante que a ArrayList que possue os períodos com qualificadores não possua períodos iguais 
						periodoC.add(periodo.get(i));
						contr=1;
					}
					for(int z = 0; z< qualif.size(); z++){ //garante que a ArrayList que possue os qualificadores encontrados não possua qualificadores repetidos
						for(int y = 0; y< sugest.size(); y++){
							if(qualif.get(z) != sugest.get(y)){
								contr2 = 0;
							}else{
								contr2 = 1;
							}
						}
					}
					if(contr2 == 0){ //caso não possua o qualificador "z" na ArrayList, ele será adicionado
						sugest.add(qualif.get(j));
					}
					conts++;
                                        controle=1;
				}
			}
			contr=0;
		}
		
		String aux;
		
		//retira as tabulações dos períodos
		for(int e=0; e<periodoC.size(); e++){
			aux = periodoC.get(e);
			periodoAux.add(aux.replaceAll("\t", ""));
		}
		//retira as quebras de linha dos períodos
		for(int e=0; e<periodoAux.size(); e++){
			aux = periodoAux.get(e);
			periodoErro.add(aux.replace("\n", ""));
		}
                
               
                if(controle==1){
                    //resultado = resultado + "<br />EVITE O USO DE QUALIFICADORES COMO: <br />";
                    resultado4 = resultado4 + "\n\nEVITE O USO DE QUALIFICADORES COMO: \n\n";
                    //control ="";
                    //System.out.println("\nEvite o uso de qualificadores como: ");
                    for(int x =0; x< conts; x++){
                            //resultado = resultado + sugest.get(x) + "<br /><br />";
                            resultado4 = resultado4 + sugest.get(x) + "\n";
                            //System.out.println(sugest.get(x));
                    }
                    
                    resultado = resultado + "PERÍODOS QUE UTILIZAM ESSES QUALIFICADORES: <br />";
                    resultado4 = resultado4 + "\nPERÍODOS QUE UTILIZAM ESSES QUALIFICADORES: \n";
                    
                    //System.out.println("\nPeríodos que utilizam esses qualificadores: ");
                    for(int h =0; h< periodoErro.size(); h++){
                        resultado = resultado + periodoErro.get(h) + "<br /><br />";
                        resultado4 = resultado4 + periodoErro.get(h) + "\n";
                        //System.out.println(periodoErro.get(h));

                    }
                }
                /*
                else{
                    control="Parabéns! O \"NOME DO PROJETO AQUI\" não identificou nenhum erro em seu texto." ;
                }*/
	}

        //método que sugere evitar uso de conjunções conclusivas no início dos períodos.
	public void conjConclusivas(String txt, int op){
		
		int i=0, contp=0, contc=0, contpar=1, conta =1, contm=0, contat=0, contpe=0, contp2=0, contpc=0, aux3 =0;
		String aux;
                
		ArrayList<String> palavras = new ArrayList<String>();
		ArrayList<String> periodo = new ArrayList<String>();
		ArrayList<String> contin = new ArrayList<String>();
		ArrayList<String> array = new ArrayList<String>();
		ArrayList<Integer> array2 = new ArrayList<>();
                
                
                ArrayList<String> paragrafo = new ArrayList<String>();
		
		/*
		for(int j = 0; j< txt.length(); j++){
			if(txt.charAt(j) == '.'){
				contpe++;
			}
		}*/
		
		StringTokenizer st = new StringTokenizer(txt, ".");
		
		while (st.hasMoreTokens()){
			periodo.add(st.nextToken());
			contpe++;
		}
			
		
		
		String aux2;

		String palav="";
		contin.add("Mesmo que");
		contin.add("Logo");
		contin.add("Por conseguinte");
		contin.add("Consequentemente");
		contin.add("Por isso");
		contin.add("Assim sendo");
		contin.add("Desse modo");
		contin.add("Deste modo");
		contin.add("Pois");
		contin.add("Portanto");
		contin.add("Sendo assim");
                contin.add("Assim");
		contin.add("Dessa forma");
		contin.add("Desta forma");
		contin.add("Por consequência");
		contin.add("Em vista disso");
		contin.add("À vista disso");
		contin.add("Dessa Maneira");
		contin.add("Desta Maneira");
		contin.add("Isto posto");
		contin.add("Destarte");
		contin.add("Dessarte");
		contin.add("Por isto");
		contin.add("Então");
		contin.add("Contudo");
                contin.add("Por conta disso");
		
		//separa os períodos em uma ArrayList
		for(int j = 0; j< periodo.size(); j++){ //percorre todos os periodos
			
			
			
			for(int x = 0; x< periodo.get(j).length(); x++){ //percorre um periodo especifico
				
				if(periodo.get(j).charAt(x) == ' '){
					
					contp2++;
					palav=palav+ " ";
				}else if (periodo.get(j).charAt(x) != ' ' && contp2 <= 3){
					
					palav = palav + "" + periodo.get(j).charAt(x);
					
					for(int y = 0; y< contin.size(); y++){
						
						if(palav.toLowerCase().contains(contin.get(y).toLowerCase())){
						
							array.add(contin.get(y));
							array2.add(j);
							x = periodo.get(j).length();
                                                        y = contin.size();
							contpc++;
							
						}
					}
				}
				
			}
			
			palav="";
			contp2=0;
		}
                
		if(contpc > 0){
                    //control ="";
                    for (int m=0; m <contpc; m++){
                            if(array.get(m) != null){
                                //resultado = resultado + "<br />EVITE O USO DE \"" + array.get(m) + "\" NO INÍCIO DAS FRASES.<br />";
                                //resultado = resultado + "<br />PERÍODO QUE UTILIZA \"" + array.get(m) + "\": <br />" + periodo.get(array2.get(m)) + "<br /><br />";
                                
                                resultado4 = resultado4 + "\n\nEVITE O USO DE \"" + array.get(m) + "\" NO INÍCIO DAS FRASES.";
                                resultado4 = resultado4 + "\nPERÍODO QUE UTILIZA \"" + array.get(m) + "\": \n" + periodo.get(array2.get(m)) + "\n";
                                    //System.out.println("\n\nEvite o uso de \"" + array.get(m) + "\" no início das frases.");
                                    //System.out.println(periodo.get(array2.get(m)));

                            }
                    }
                }
                /*
                else{
                    control="Parabéns! O \"NOME DO PROJETO AQUI\" não identificou nenhum erro em seu texto." ;
                }*/
	
	}
	
        //Verifica se sujeito está bem definido no início das frases.
        
	public void colocacaoSujeito(String txt, int op) throws FileNotFoundException{
		
		
		int i=0, contp=0, contc=0, contpar=1, conta =1, contm=0, contat=0, contpe=0, contp2=0, contpc=0, aux3 =0;
		int cont=0;
		ArrayList<String> array = new ArrayList<String>();
		ArrayList<Integer> array2 = new ArrayList<>();
		ArrayList<String> periodo = new ArrayList<String>();
		ArrayList<String> regras = new ArrayList<String>();
		
		/*
		for(int j = 0; j< txt.length(); j++){
			if(txt.charAt(j) == '.'){
				contpe++;
			}
		}*/
		int cont4=0;
                for(int j = 0; j< txt.length(); j++){
                        if(txt.charAt(j) == '\t'){
                                cont4++;
                        }
                }
                String array1[] = new String[cont4];
                //for(int j = 0; j< txt.length(); j++){
                        array1 = txt.split("\t");

                //}
                        String aux="";
                for(int j = 1; j<= cont4; j++){
                        aux = aux +(String) array1[j];
                        //System.out.println("Array1 " + j + array1[j]);
                        //System.out.println("Aux === "+ aux);
                        //paragrafo.add(aux);

                }
               
		StringTokenizer st = new StringTokenizer(aux, ".");
		
		while (st.hasMoreTokens()){
			periodo.add(st.nextToken());
			contpe++;
		}
                
		String aux2;
		String palav="";
		
		regras.add("O");
		regras.add("Os");
		regras.add("A");
		regras.add("As");
		regras.add("Esse");
		regras.add("Este");
		regras.add("Essa");
		regras.add("Esta");
		regras.add("Esses");
		regras.add("Estes");
		regras.add("Essas");
		regras.add("Estas");
                
		
                int c, x;
                int contador=0; 
		//separa os períodos em uma ArrayList
		for(int j = 0; j< periodo.size(); j++){ //percorre todos os periodos
			if(j==0){
                            x=0;
                        }else{
                            x=1;
                        }
                        
			//Mesmo que; logo, por conseguinte, consequentemente, por isso, assim sendo, desse modo, pois, portanto, assim.
			palav="";
			while(x< periodo.get(j).length()){ //percorre um periodo especifico
				if(periodo.get(j).charAt(x) == '\n'){
                                    //System.out.println("entrou no if");
                                    x++;
                                    
                                }
				if(periodo.get(j).charAt(x) == ' ' ){
                                    
					contp2++;
                                        x++;
				}
				if (periodo.get(j).charAt(x) != ' ' && contp2 < 1){
					
					palav = palav + "" + periodo.get(j).charAt(x);
					
				}
                                x++;
			}
                        //System.out.println("palav= " + palav);
                        int contrl=0;
			          
			for(int y = 0; y< regras.size(); y++){
				if(palav.toLowerCase().equals(regras.get(y).toLowerCase())){
					
					array2.add(-1); 
                                        //System.out.println("array= " + array2.get(j));
					contpc++;
                                        contrl=1;
                                        y = regras.size();
                                        
				}else{
                                    contrl=2;
                                }
			}
                        if(contrl == 2){
                            array2.add(j);
                            //System.out.println("entrou");
                            //control="";
                        }
                        /*
                        else{
                            control="Parabéns! O \"NOME DO PROJETO AQUI\" não identificou nenhum erro em seu texto." ;
                        }*/
			contp2=0;
		}
		
		int teste=0, controle=0, contou=0;
		
                
                int ind=0;
                            for (int l=0; l < periodo.size(); l++){
                                if(array2.get(l) != -1){
                                    if(controle == 0){
                                        //resultado = resultado+ "<br />O SUJEITO PARECE NÃO ESTAR BEM DEFINIDO OU NÃO ESTÁ BEM LOCALIZADO NO INÍCIO DAS SEGUINTES FRASES: <br />";
                                        resultado4 = resultado4+ "\n\nO SUJEITO PARECE NÃO ESTAR BEM DEFINIDO OU NÃO ESTÁ BEM LOCALIZADO NO INÍCIO DAS SEGUINTES FRASES: \n\n";
                                            //control ="";
                                            controle =1;
                                    }
                                    //resultado = resultado + periodo.get(l) + "." + "<br /><br />";
                                    resultado4 = resultado4 + periodo.get(l) + "." + "\n";
                                }
                                
                            }
                            /*
                            if(resultado == ""){
                                control="Parabéns! O \"NOME DO PROJETO AQUI\" não identificou nenhum erro em seu texto." ;
                            }*/
                            
                            if (resultado4 != ""){
                                Document document = new Document();
                                try {

                                    PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Brena Maia\\Downloads\\Resultado-Analise-Texto.pdf"));
                                    document.open();

                                    // adicionando um parágrafo no documento
                                    document.add(new Paragraph("Resultado da análise do seu texto: \n\n\n" + resultado4));
                                    
                                }
                                catch(DocumentException de) {
                                    System.err.println(de.getMessage());
                                }
                                document.close();
                                System.out.println("RESULTADO= " + resultado4);
                            }
	
	}
	
	
}
