package com.npb.gp.interfaces.dao;

import java.util.ArrayList;
import java.util.List;

import com.npb.gp.domain.core.GpButtonGroup;


public interface IGpButtonGroupDao {

	public GpButtonGroup insert(GpButtonGroup wizard);

	void delete(long screenId) throws Exception;

	ArrayList<GpButtonGroup> getAllGroupsForScreen(long screenId) throws Exception;

	ArrayList<GpButtonGroup> getAllGroupsForScreenAndType(long screenId, String type) throws Exception;

	void update(GpButtonGroup gpButtonGroup);
}
