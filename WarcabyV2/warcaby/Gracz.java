package warcaby;

public class Gracz {
	private String nazwa;
	private int ileWygranych = 0;
	private int ilePrzegranych = 0;
	
	public Gracz(String naz){
		nazwa = naz;
	}
	public String getNazwa(){
		return nazwa;
	}
	public int getIleWygranych() {
		return ileWygranych;
	}
	public void setIleWygranych() {
		ileWygranych++;
	}
	public void setIleWygranych(int ile) {
		ileWygranych = ile;
	}
	public int getIlePrzegranych() {
		return ilePrzegranych;
	}
	public void setIlePrzegranych() {
		ilePrzegranych++;
	}	
	public void setIlePrzegranych(int ile) {
		ilePrzegranych = ile;
	}	
}