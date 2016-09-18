package ses.controller.sys.oms;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
public class NullValueHandler implements TypeHandler<String>{
	@Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }

    @Override
    public void setParameter(PreparedStatement pstmt, int index, String value, JdbcType jdbcType) throws SQLException {
    	/*if(value==null){
    		if(jdbcType == JdbcType.VARCHAR){
    			pstmt.setString(index,"");
    		}else if (jdbcType == JdbcType.INTEGER) {
    			pstmt.setInt(index,0);
			}else if (jdbcType == JdbcType.DOUBLE) {
				pstmt.setDouble(index, Double.valueOf(0));
			}
    		
    	}*/
        if(value == null && jdbcType == JdbcType.VARCHAR){//闁告帇鍊栭弻鍥ㄥ閻樻彃寮抽柣銊ュ瀵剟寮弶鍖℃嫹闁哄嫷鍨伴幆浣圭▔缁ㄥ増ll
            pstmt.setString(index,"");//閻犱礁澧介悿鍡氥亹閹惧啿顤呴柛娆忓�閺嗙喖鎯冮崟顐嫹濞戞捁娅ｉ埞鏍拷濡ゅ喚鍎婂☉鎿勬嫹
        }else{
            //pstmt.setString(index,value);//濠碘�鍊归悘澶嬬▔瀹ュ嫯绀媙ull闁挎稑鑻崹顖炴儎鐎涙ê澶嶉悹浣稿⒔閻ゅ棝宕ｉ崒娑欐闁汇劌瀚敓鑺ョ▔缁ㄦ亞lue
        	if(jdbcType == JdbcType.INTEGER){
        		pstmt.setInt(index,Integer.parseInt(value));
        	}else {
        		pstmt.setString(index,value);//濠碘�鍊归悘澶嬬▔瀹ュ嫯绀媙ull闁挎稑鑻崹顖炴儎鐎涙ê澶嶉悹浣稿⒔閻ゅ棝宕ｉ崒娑欐闁汇劌瀚敓鑺ョ▔缁ㄦ亞lue
			}
        }
    }
}
