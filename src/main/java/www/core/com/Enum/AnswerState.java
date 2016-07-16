package www.core.com.Enum;

public enum AnswerState implements Enum {
	NoAnser{
		@Override
		public String show() {
			return "不需要解答";
		}	
	},
	NotAnser{
		@Override
		public String show() {
			return "未解答";
		}	
	},
	HaveAnser{
		@Override
		public String show() {
			return "已解答";
		}	
	}
}
