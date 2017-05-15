package com.npb.gp.dao.mysql.support.screen;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 03/14/2014</br>
 * @since .35
 *        </p>
 *
 *        Insert class supporting the GpScreenDao class
 *        </p>
 *        
 *        Modified Date: 09/10/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as image_src to save the image sources
 * 
 *        Modified Date: 10/09/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as header and footer for the component as card in
 *        under the widgets
 * 
 *        Modified Date: 04/09/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variable as target_url for the component as link
 * 
 *        Modified Date: 05/08/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Removed the variable as columns
 * 
 *        Modified Date: 01/07/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added the new variables as rows and columns
 * 
 *        Modified Date: 19/06/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Removed the variables X and Y
 * 
 *        Modified Date: 04/06/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variable as css_class
 * 
 *        Modified Date: 25/05/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as portraitOffsetX, portraitOffsetY,
 *        landscapeOffsetX and landscapeOffsetY
 * 
 *        Modified Date: 12/05/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Changed the parent to String and added new variable as parent_id
 *
 *        Modified Date: 19/03/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        added the variables as events and font_size
 * 
 *        Modified Date: 24/03/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        removed the font_size for temporarily
 *
 */
public class InsertScreenWidget extends SqlUpdate {

	public static String SQL_INSERT_WIDGET = "";

	public InsertScreenWidget(DataSource dataSource) {
		super(dataSource, SQL_INSERT_WIDGET);
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("parent_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("screen_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("is_container", Types.VARCHAR));
		super.declareParameter(new SqlParameter("supports_label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("ui_technology", Types.VARCHAR));
		super.declareParameter(new SqlParameter("width", Types.BIGINT));
		super.declareParameter(new SqlParameter("height", Types.BIGINT));
		// super.declareParameter(new SqlParameter("x", Types.BIGINT));
		// super.declareParameter(new SqlParameter("y", Types.BIGINT));
		super.declareParameter(new SqlParameter("data_binding_context", Types.VARCHAR));
		super.declareParameter(new SqlParameter("verb_binding_context", Types.VARCHAR));
		super.declareParameter(new SqlParameter("noun_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("noun_attribute_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("parent_name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("number_of_children", Types.BIGINT));
		super.declareParameter(new SqlParameter("extended_attributes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("event_verb_combo", Types.VARCHAR));
		super.declareParameter(new SqlParameter("verb_target", Types.VARCHAR));
		super.declareParameter(new SqlParameter("portrait_x", Types.BIGINT));
		super.declareParameter(new SqlParameter("portrait_y", Types.BIGINT));
		super.declareParameter(new SqlParameter("portrait_height", Types.BIGINT));
		super.declareParameter(new SqlParameter("portrait_width", Types.BIGINT));
		super.declareParameter(new SqlParameter("landscape_x", Types.BIGINT));
		super.declareParameter(new SqlParameter("landscape_y", Types.BIGINT));
		super.declareParameter(new SqlParameter("landscape_height", Types.BIGINT));
		super.declareParameter(new SqlParameter("landscape_width", Types.BIGINT));
		super.declareParameter(new SqlParameter("events", Types.VARCHAR));
		super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("last_modified_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("last_modified_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("portrait_offset_x", Types.BIGINT));
		super.declareParameter(new SqlParameter("portrait_offset_y", Types.BIGINT));
		super.declareParameter(new SqlParameter("landscape_offset_x", Types.BIGINT));
		super.declareParameter(new SqlParameter("landscape_offset_y", Types.BIGINT));
		super.declareParameter(new SqlParameter("css_class", Types.VARCHAR));
		super.declareParameter(new SqlParameter("rows", Types.BIGINT));
		// super.declareParameter(new SqlParameter("columns", Types.BIGINT));
		super.declareParameter(new SqlParameter("target_url", Types.VARCHAR));
		super.declareParameter(new SqlParameter("header", Types.VARCHAR));
		super.declareParameter(new SqlParameter("footer", Types.VARCHAR));
		super.declareParameter(new SqlParameter("image_src", Types.VARCHAR));
		super.declareParameter(new SqlParameter("extra_verb_info", Types.VARCHAR));
		super.declareParameter(new SqlParameter("custom_verb_info", Types.VARCHAR));
		super.declareParameter(new SqlParameter("wizard_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("screen_wizard_sequence_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("data_binding_target_noun_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("data_binding_target_noun_attr_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("section_position", Types.BIGINT));
		super.declareParameter(new SqlParameter("group_values",Types.VARCHAR));

		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);
	}

}
