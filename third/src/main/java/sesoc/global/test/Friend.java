package sesoc.global.test;

public class Friend {
	private String name;
	private int age;
	private String phone;
	public Friend() {
		// TODO Auto-generated constructor stub
	}
	public Friend(String name, int age, String phone) {
		super();
		this.name = name;
		this.age = age;
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Friend [name=" + name + ", age=" + age + ", phone=" + phone + "]";
	}
	
}
