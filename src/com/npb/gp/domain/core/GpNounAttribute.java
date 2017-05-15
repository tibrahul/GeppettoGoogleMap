package com.npb.gp.domain.core;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

;
/**
 * @author Dan Castillo<br>
 *         Date Created: 10/31/2012
 * @since .35</p>
 * 
 *        This is one of the key model classes for the system, its purpose is to
 *        encapsulate the information that will lead to model classes in the
 *        generated code. The class captures the "field" information for what
 *        eventually becomes part of the generated model classes. The class is
 *        used in conjunction with the GpNoun class.
 *
 *        Please note that this class was originally part of the POC for
 *        Geppetto which is designated as version .1 and was used in version .2
 *        and .3 I decided to have a fresh start for version .35 and beyond.
 *        </p>
 *
 * 
 * 
 *        <b>Modified Date:&nbsp;&nbsp;</b>12/12/2012</br> <b>Modified By:</b>
 *        Dan Castillo</br> <b>Modification Description:</b></br> added
 *        subtypemodifiervalue field - the purpose of this field is to provide
 *        more information</br> about the noun attribute - for example if the
 *        noun attribute type is a list</br> it will contain the list type (list
 *        of ints, or string, or dates or other types)</p>
 * 
 * 
 *        <b>Modified Date:&nbsp;&nbsp;</b>02/04/2013</br> <b>Modified By:</b>
 *        Dan Castillo</br> <b>Modification Description:</b></br> added
 *        ispart_of_unique_key field - the purpose of this field is to indicate
 *        whether</br> or not the nounattribute is used as part of an index that
 *        identifies that makes</br> noun unique in a list of nouns of the same
 *        type</br> I.E. - if the noun is CAR then the nounattribute for
 *        VIN_NUMBER would have its</br> ispart_of_unique_key property set to
 *        true
 * 
 * 
 *        <b>Modified Date: 12/03/2015 Modified By: Suresh Palanisamy</b>
 * 
 *        <p>
 *        Added "@JsonIgnoreProperties(ignoreUnknown=true)"
 *        </p>
 * 
 * 
 *        <b>Modified Date: 25/03/2015 Modified By: Suresh Palanisamy</b>
 * 
 *        <p>
 *        Added equals to compare the two objects
 *        </p>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpNounAttribute {

	private long id;
	private long nounid;
	private String name;
	private String label;
	private long default_activity_id;
	private String technicalname; // i doont think I need this any more - Dan
									// 4/8/2014
	private String description;
	private long base_attr_type_id;
	private boolean ispart_of_unique_key;
	private String datalength;
	private String notes;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	private String relationships;
	private boolean is_secondary_noun;
	
	public boolean isIs_secondary_noun() {
		return is_secondary_noun;
	}
	public void setIs_secondary_noun(boolean is_secondary_noun) {
		this.is_secondary_noun = is_secondary_noun;
	}
	/*
	 * These two attributes are added to store the noun id and name , if the attribute type is a NOUN / List of noun , when its std types then only modifier name is stored
	 * */
	private long modifierId;
	private String modifierName;

	public long getBase_attr_type_id() {
		return base_attr_type_id;
	}
	public void setBase_attr_type_id(long base_attr_type_id) {
		this.base_attr_type_id = base_attr_type_id;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNounid() {
		return nounid;
	}

	public void setNounid(long nounid) {
		this.nounid = nounid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public long getDefault_activity_id() {
		return default_activity_id;
	}
	public void setDefault_activity_id(long default_activity_id) {
		this.default_activity_id = default_activity_id;
	}
	public String getTechnicalname() {
		return technicalname;
	}

	public void setTechnicalname(String technicalname) {
		this.technicalname = technicalname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isIspart_of_unique_key() {
		return ispart_of_unique_key;
	}

	public void setIspart_of_unique_key(boolean ispart_of_unique_key) {
		this.ispart_of_unique_key = ispart_of_unique_key;
	}

	public String getDatalength() {
		return datalength;
	}

	public void setDatalength(String datalength) {
		this.datalength = datalength;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public Date getLastmodifieddate() {
		return lastmodifieddate;
	}

	public void setLastmodifieddate(Date lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}

	public long getLastmodifiedby() {
		return lastmodifiedby;
	}

	public void setLastmodifiedby(long lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}
	
	public String getRelationships() {
		return relationships;
	}
	public void setRelationships(String relationships) {
		this.relationships = relationships;
	}
	public long getModifierId() {
		return modifierId;
	}
	public void setModifierId(long modifierId) {
		this.modifierId = modifierId;
	}
	public String getModifierName() {
		return modifierName;
	}
	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof GpNounAttribute)
				&& this.id == ((GpNounAttribute) obj).getId();
	}

}
