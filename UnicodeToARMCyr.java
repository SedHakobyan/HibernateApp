package com.bee.am;

public class UnicodeToARMCyr {
    static final String[] armCyr = { "А", "а", "Б", "б", "В", "в", "Г", "г", "Д", "д", "Е", "е", "Ё", "ё", "Ж", "ж", "З", "з", "�?", "и", "І", "і", "Ї", "ї", "Й", "й", "К", "к", "Л", "л", "Љ", "љ", "М", "м", "Н", "н", "Њ", "њ", "О", "о", "П", "п", "Р", "р", "С", "с", "Т", "т", "У", "у", "Ў", "ў", "Ф", "ф", "Х", "х", "Ц", "ц", "Ч", "ч", "Ш", "ш", "Щ", "щ", "Ъ", "ъ", "Ы", "ы", "Ь", "ь", "Э", "э", "Ю", "ю", "Я", "я", "§" };
    static final String[] arm = { "Ա", "ա", "Բ", "բ", "Գ", "գ", "Դ", "դ", "Ե", "ե", "Զ", "զ", "Է", "է", "Ը", "ը", "Թ", "թ", "Ժ", "ժ", "Ի", "ի", "Լ", "լ", "Խ", "խ", "Ծ", "ծ", "Կ", "կ", "Հ", "հ", "Ձ", "ձ", "Ղ", "ղ", "Ճ", "ճ", "Մ", "մ", "Յ", "յ", "Ն", "ն", "Շ", "շ", "Ո", "ո", "Չ", "չ", "Պ", "պ", "Ջ", "ջ", "Ռ", "ռ", "Ս", "ս", "Վ", "վ", "Տ", "տ", "Ր", "ր", "Ց", "ց", "Ւ", "ւ", "Փ", "փ", "Ք", "ք", "Օ", "օ", "Ֆ", "ֆ", "և" };
    public static String armCyrToArm(String inputStr)
    {
        String output = inputStr;
        for (int i = 0; i < armCyr.length; i++) {
            output = output.replace(armCyr[i], arm[i]);
        }
        return output;
    }

    public static String UToARMCyr(String inputStr)
    {
        String output = inputStr;
        for (int i = 0; i < arm.length; i++) {
            output = output.replace(arm[i],armCyr[i]);
        }
        return output;

    }
    protected static String rvs(String str)
    {
        char a = 0;
        int b;
        StringBuilder res=new StringBuilder("");
        for (int i=0;i<str.length();i++)
        {
            a = str.charAt(i);
            b=a-4;
            res.append((char) b);
        }
        return res.toString();
    }
}
