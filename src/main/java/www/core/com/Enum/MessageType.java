package www.core.com.Enum;

public enum MessageType  implements Enum  {
	addGroup
	{
		@Override
		public String show() {
			return "���Ȧ����Ϣ";
		}
	},
	exitGroup{
		@Override
		public String show(){
			return "�˳����Ȧ����Ϣ";
		}
	},
	newReply{
		@Override
		public String show() {
			return "�µĻظ�";
		}	
	},
	newReplyBook{
		@Override
		public String show() {
			return "�µ�ͼ��ظ�";
		}
	},
	applyGroup{
		@Override
		public String show() {
			return "Ȧ������";
		}
	}
	,READadd
	{
		@Override
		public String show() {
			return "�Ѷ����Ȧ����Ϣ";
		}
	},
}
