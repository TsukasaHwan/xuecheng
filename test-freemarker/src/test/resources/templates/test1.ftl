<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!<br/>
<h2>遍历数据模型中的学生信息（数据名为students的list集合）</h2>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>金额</td>
        <td>出生日期</td>
    </tr>
        <#list students as student>
            <tr>
                <td>
                    ${(student_index + 1)!""}
                </td>
                <td <#if student.name == "李四">style="background: red" </#if>>
                    ${(student.name)!""}
                </td>
                <td>
                    ${(student.age)!""}
                </td>
                <td <#if (student.money > 5000)>style="background: blue" </#if>>
                    ${(student.money)!""}
                </td>
                <td>
                    ${(student.birthday?string("yyyy/MM/dd HH:mm:ss"))!""}<br/>
                    ${(student.birthday?date)!""}
                </td>
            </tr>
        </#list>
</table>
<br/>
<h2>获取map中的数据</h2>
姓名：${studentMap['student1'].name}<br/>
年龄：${studentMap['student1'].age}<br/>
<hr>
姓名：${studentMap.student2.name}<br/>
年龄：${studentMap.student2.age}<br/>
<br/>
<#list studentMap?keys as key>
    姓名：${studentMap[key].name}<br/>
    年龄：${studentMap[key].age}<br/>
</#list>
${point?c}
</body>
</html>