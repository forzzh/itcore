package www.core.com.Enum;

public enum StatusEnum  implements Enum {
	delete{//ɾ��״̬
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "ɾ��";
		}
	},
	nodelete{//��ɾ��״̬
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "����";
		}
	},
	//Ȧ�����ͬ��״̬
	agree{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "2";
		}
	},
	//Ȧ����Ӳ�ͬ��
	noagree{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "3";
		}
	},
	
	read{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "3";
		}
	},
	noread{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "4";
		}
	},
	ok{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "ͬ��";
		}
	},
	no{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "��ͬ��";
		}
	},//Ȧ�������״̬
	pending{
		@Override
		public String show() {
			return "�����";
		}		
	}
	
}
