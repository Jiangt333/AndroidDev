package com.example.myapplication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DBOperator
{	private final Connection con;
    private PreparedStatement stmt = null;
    public DBOperator()
    {
        con = MySQLConnections.getConnection();
    }

    public void DBClose()
    {   try
    {   con.close();
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    }

    public ResultSet DBExcec(String SqlStr)
    {
        try
        {   stmt = (PreparedStatement) con.prepareStatement(SqlStr);
            // 关闭事务自动提交 ,这一行必须加上
            con.setAutoCommit(false);
            ResultSet rs  = stmt.executeQuery();
            //rs.close();
            stmt.close();
            //必须在外部调用rs.close()
            return rs;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void DBGetList(String uname)
    {
        ResultSet rs = null;
        try
        {
            String st = "";
            stmt = (PreparedStatement) con.prepareStatement(st);
            rs = stmt.executeQuery();
            while(rs.next())
            {

            }
            rs.close();
            stmt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void DBGetInfoList(String target)
    {
        ResultSet rs = null;
        try
        {
            String st = "";
            stmt = (PreparedStatement) con.prepareStatement(st);
            rs = stmt.executeQuery();
            while(rs.next())
            {

            }
            rs.close();
            stmt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }
}