package www.core.com.Enum;

public enum UserEnum  implements Enum{
	delete{//ɾ��״̬
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "�Ѿ�ɾ��";
		}
	},
	active{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "�Ѽ���";
		}
	},
	
	
	noactive{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "δ����";
		}
	},
}
