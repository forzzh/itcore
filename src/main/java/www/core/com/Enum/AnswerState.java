package www.core.com.Enum;

public enum AnswerState implements Enum {
	NoAnser{
		@Override
		public String show() {
			return "����Ҫ���";
		}	
	},
	NotAnser{
		@Override
		public String show() {
			return "δ���";
		}	
	},
	HaveAnser{
		@Override
		public String show() {
			return "�ѽ��";
		}	
	}
}
