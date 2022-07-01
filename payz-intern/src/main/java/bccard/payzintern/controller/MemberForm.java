package bccard.payzintern.controller;

/*
MemberForm 클래스 생성
-> 멤버변수 String name이랑
-> createMemberForm.html의 input 태그의 name=”name”과 매칭
*/
public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
