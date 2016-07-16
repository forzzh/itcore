package www.core.com.Enum;

public enum UserEnum  implements Enum{
	delete{//É¾³ý×´Ì¬
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "ÒÑ¾­É¾³ý";
		}
	},
	active{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "ÒÑ¼¤»î";
		}
	},
	
	
	noactive{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "Î´¼¤»î";
		}
	},
}
