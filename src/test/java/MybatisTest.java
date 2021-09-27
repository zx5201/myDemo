import dao.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.User;

import java.io.IOException;
import java.io.Reader;

public class MybatisTest {

    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml"); // 加载配置文件
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); //创建sqlSessionFactory对象
        SqlSession sqlSession = sqlSessionFactory.openSession(); // 获取sqlSession对象
//        UserDao userDao = sqlSession.getMapper(UserDao.class); // 获取映射接口
        //另外一种写法就是把statement解析的数据传递进去
        Object o = sqlSession.selectOne("dao.UserDao.getUser", 1);
        System.out.println(o); //


        int i= 1;
        int a = i++ +  ++i  + i++ * i++ + i++;

        System.out.println(a);
        System.out.println(i);
    }
}
