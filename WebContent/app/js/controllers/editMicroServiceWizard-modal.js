/**
 * Since Geppetto v.90
 * Created: July 15, 2015
 * @author Pravin
 * 
 * Note: This controller will handle all the modal's related to the activity
 */

app.controller('editMicroServiceWizardModalCtrl',
		['$scope', '$log', '$modalInstance', 'modalDataObj','$http','RestURL','$filter','MicroServiceWizardFactory',
		 function($scope, $log, $modalInstance, modalDataObj,$http,RestURL,$filter,MicroServiceWizardFactory){
			
	var self=$scope;
	
	/**
	 * reference for external scope object for ui grid 
	 */
	self.scope=$scope;
	
	/**
	 * close handler for modal
	 */
	self.close=function(){
		  $modalInstance.dismiss('cancel');	
	};	
	
	self.modalObj={
			/**
			 * grid options used to display the device types
			 */
			gridOptions:{},
			editCallback:null,
			deleteCallback:null,
			action:''
		};
	/**
	 * deletable entity
	 */
	self.deleteEntity=null;
	/**
	 * 
	 */
	$scope.Activities =[];
	self.init=function(){
		$scope.ActivityName=modalDataObj.wizard;
		$scope.microservice_name=$scope.ActivityName[0].microservice_name;
		self.actionButtons='<div style="text-align:center;" >'+
		'<button ng-hide="{{row.entity.activity_name == null}}"  class="btn btn-danger btn-sm" '+
			'ng-click="grid.appScope.deleteCallback(grid,row)" style="margin:2px;">'+
			'<i class="fa fa-trash"></i>'+
		'</button></div>';
		
		
		self.modalObj.gridOptions={
				data : 'ActivityName',
				rowHeight:40,
				columnDefs:[
				    {name:'id',field:'id',visible:false},
				    {field: 'activity_name', name: 'Activities'},
				    {field: 'activity_id', name: 'Activityids', visible:false},  
					{name:'Action', cellTemplate: self.actionButtons}
				]
		};
		
		self.modalObj.deleteCallback=modalDataObj.deleteCallback;
		self.modalObj.editCallback=modalDataObj.editCallback;
		
	};
	
	/**
	 * grid delete button action listener 
	 */
	self.deleteCallback=function(grid, row){
		self.deleteEntity=row.entity;
	}
	
	/**
	 * edit action has been triggered when user clicks!
	 */
	
	self.editCallback=function(grid,row){
		//self.close();
		//self.modalObj.editCallback(row.entity);
		self.modalObj.editCallback(row.entity);
	}
	
	function myCallbackFunction(response) {
		modalDataObj.wizard=response;
		self.init();
		}
	
	/**
	 * delete action has been triggered when user clicks on delete screen!
	 */
	self.deleteCall=function(){
		$log.log("triggering delete:",self.deleteEntity);
		self.close();
		self.modalObj.deleteCallback(self.deleteEntity);
	};
	
	
	/**
	 * cancel handler for delete action
	 */
	self.cancelDelete=function(){
		$log.log("canceling delete!");
		self.deleteEntity=null;
	};
	
	self.init();
	
	
}])