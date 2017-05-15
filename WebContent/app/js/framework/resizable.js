
var overlappingscreen = false;
var dragscreenobjectid=0;
var levelscreenid = 0;

//single container check
var overlappingsingle = false;
var dragsingleobjectid=0;
var levelsingleid = 0;

//multi container check
var overlappingmulti = false;
var dragmultiobjectid=0;
var levelmultiid = 0;

function Resizable(wrapper) {
    var self = this,
        id = wrapper.attr('id'),
        handle,
        element = wrapper.children().not('.resize-handle').first(),
        resizeHandles = $(
                            '<span class="resize-handle resize-handle-n"></span>' +
                            '<span class="resize-handle resize-handle-ne"></span>' +
                            '<span class="resize-handle resize-handle-e"></span>' +
                            '<span class="resize-handle resize-handle-se"></span>' +
                            '<span class="resize-handle resize-handle-s"></span>' +
                            '<span class="resize-handle resize-handle-sw"></span>' +
                            '<span class="resize-handle resize-handle-w"></span>' +
                            '<span class="resize-handle resize-handle-nw"></span>'
                        );

    if (wrapper.find('.resize-handle').length === 0)
        wrapper.prepend(resizeHandles);

    var elementWidth = element.outerWidth(true);
    var elementHeight = element.outerHeight(true);

    switch (AxeControllerScope.name) {
        case 'desktop': {
            switch (element.data('role')) {
                case 'checkbox':
                case 'radio':
                case 'label': {
                    elementWidth = element.width();
                    elementHeight = element.height();
                    break;
                }
                case 'accordion': {
                    element.siblings('span.resize-handle:not(.resize-handle-e, .resize-handle-w)').remove();
                    break;
                }
                case 'table': {
                    element.css('position', 'static');
                    elementWidth = element.width();
                    elementHeight = element.height();
                    break;
                }
                case 'hrule': {
                    element.siblings('span.resize-handle:not(.resize-handle-e, .resize-handle-w)').remove();
                    break;
                }
                case 'vrule': {
                    element.siblings('span.resize-handle:not(.resize-handle-n, .resize-handle-s)').remove();
                    break;
                }
            }
            break;
        }
        case 'tablet': {
            switch (element.data('role')) {
                case 'checkbox':
                case 'radio':
                case 'label': {
                    elementWidth = element.width();
                    elementHeight = element.height();
                    break;
                }
                case 'accordion': {
                    element.siblings('span.resize-handle:not(.resize-handle-e, .resize-handle-w)').remove();
                    break;
                }
                case 'table': {
                    element.css('position', 'static');
                    elementWidth = element.width();
                    elementHeight = element.height();
                    break;
                }
                case 'hrule': {
                    element.siblings('span.resize-handle:not(.resize-handle-e, .resize-handle-w)').remove();
                    break;
                }
                case 'vrule': {
                    element.siblings('span.resize-handle:not(.resize-handle-n, .resize-handle-s)').remove();
                    break;
                }
            }
            break;
        }
        case 'mobile': {
            switch (element.data('role')) {
                case 'checkbox':
                case 'label': {
                    elementWidth = element.width();
                    elementHeight = element.height();
                    break;
                }
                case 'radio':
                case 'list': {
                    element.siblings('span.resize-handle:not(.resize-handle-e, .resize-handle-w)').remove();
                    break;
                }
                case 'toggle': {
                    element.siblings('span.resize-handle').remove();
                    break;
                }
            }
            break;
        }
    }

    wrapper.css({
        width: elementWidth,
        height: elementHeight
    });

    element.css({
        position: 'relative',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%'
    });

    var oldWrapperWidth = wrapper.outerWidth(true),
        oldWrapperHeight = wrapper.outerHeight(true);

    $('#' + id + ' .resize-handle')
                        .on('dragstart', false)
                        .on('mousedown', onMouseDown);

    function onMouseDown(e) {
        handle = e.target.className.slice('resize-handle resize-handle-'.length);

        $(document).on('mousemove.resizable', $(this), onMouseMove);
        $(document).on('mouseup.resizable', $(this), onMouseUp);

        /* Disable select */
        return false;
    }

    function onMouseMove(e) {
        var top = wrapper.offset().top;
        var bottom = $(window).height() - top - wrapper.height();
        var left = wrapper.offset().left;
        var right = $(window).width() - left - wrapper.width();
        var newWidth = null, newHeight = null;

        switch (handle) {
            case 'n': {
                wrapper.css({
                    top: 'auto',
                    bottom: bottom
                });
                newHeight = ($(window).height() - bottom) - e.pageY;
                break;
            }
            case 'ne': {
                wrapper.css({
                    top: 'auto',
                    bottom: bottom,
                    left: left,
                    right: 'auto'
                });
                newWidth = e.pageX - left;
                newHeight = ($(window).height() - bottom) - e.pageY;
                break;
            }
            case 'e': {
                wrapper.css({
                    top: top,
                    bottom: 'auto',
                    left: left
                });
                newWidth = e.pageX - left;
                break;
            }
            case 'se': {
                wrapper.css({
                    top: top,
                    bottom: 'auto',
                    left: left
                });
                newWidth = e.pageX - left;
                newHeight = e.pageY - top;
                break;
            }
            case 's': {
                wrapper.css({
                    top: top,
                    bottom: 'auto'
                });
                newHeight = e.pageY - top;
                break;
            }
            case 'sw': {
                wrapper.css({
                    top: top,
                    bottom: 'auto',
                    left: 'auto',
                    right: right
                });
                newWidth = ($(window).width() - right) - e.pageX;
                newHeight = e.pageY - top;
                break;
            }
            case 'w': {
                wrapper.css({
                    top: top,
                    bottom: 'auto',
                    left: 'auto',
                    right: right
                });
                newWidth = ($(window).width() - right) - e.pageX;
                break;
            }
            case 'nw': {
                wrapper.css({
                    top: 'auto',
                    bottom: bottom,
                    left: 'auto',
                    right: right
                });
                newWidth = ($(window).width() - right) - e.pageX;
                newHeight = ($(window).height() - bottom) - e.pageY;
                break;
            }
        }

        wrapper.css({
            width: newWidth,
            height: newHeight
        });
    }

    function onMouseUp() {
        $(document).off('.resizable');

        var newWrapperWidth = wrapper.outerWidth(true),
            newWrapperHeight = wrapper.outerHeight(true);

        /* nothing changed, resize didn't happen */
        if (newWrapperWidth === oldWrapperWidth && newWrapperHeight === oldWrapperHeight)
            return;

        $(self).triggerHandler({
            type: 'resize',
            newWidth: newWrapperWidth,
            newHeight: newWrapperHeight
        });

        oldWrapperWidth = newWrapperWidth;
        oldWrapperHeight = newWrapperHeight;
        
        /* update element info after resize */
        if (AxeControllerScope) {
            AxeControllerScope.getElementInfo(element[0]);
        }
        
    	/********************************* OVERLAPPING ISSUE FIX START *****************************/
        var screenchildren = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};
        var resizeObjId = wrapper.attr('id');
        var draggedObj = screenchildren[resizeObjId];
        
        if(draggedObj !=undefined && draggedObj.portraitY <0 ){
        	$("<div title='Warning'>Components not allowed outside screen!!</div>").dialog();
        	AxeControllerScope.outsidescreen = true;
        	AxeControllerScope.$apply();
        }else{
        	AxeControllerScope.outsidescreen = false;
        	AxeControllerScope.$apply();
        }
        for (var firstLevel in screenchildren) {
        // indicates that resize obj is inside a container
        if(draggedObj == undefined && screenchildren[firstLevel].hasOwnProperty('children')){ 
        	var dragobjid =resizeObjId;
    		var draggedobj = screenchildren[firstLevel]['children'][dragobjid];            		
            if(draggedobj != undefined ){ 
	    		var topsec = draggedobj.portraitY;
	            var leftsec = draggedobj.portraitX;
	            var widthsec = draggedobj.portrait_width;
	            var heightsec = draggedobj.portrait_height;
            }else{ // check for multilevel containers and get the dragged obj id
            	for (var secondLevel in screenchildren[firstLevel]['children']) {
            	if (screenchildren[firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                    	draggedobj = screenchildren[firstLevel]['children'][secondLevel]['children'][dragobjid];
                    	if(draggedobj!=undefined)
                    	break;
            		}
            	}
	    		var topsec = draggedobj.portraitY;
	            var leftsec = draggedobj.portraitX;
	            var widthsec = draggedobj.portrait_width;
	            var heightsec = draggedobj.portrait_height;
            }
                
                for (var secondLevel in screenchildren[firstLevel]['children']) {
                	//check if the container is single or multilevel
                	if(screenchildren[firstLevel]['children'][secondLevel]['children'] == undefined){
                		//if single 
                		if(overlappingsingle == true && dragsingleobjectid !=0 && (dragsingleobjectid == resizeObjId|| levelsingleid == resizeObjId)){
                		  $('#' + levelsingleid).css({
      	                	border: "none"
          	      	       });
      	                  
          	              $('#' + dragsingleobjectid).css({
          	                	border: "none"
          	      	      });
          	            overlappingsingle = false;
          	            dragsingleobjectid = 0;
          	            levelsingleid =0;
          	            AxeControllerScope.overlapping = false;
         				}else if(overlappingsingle == true && dragsingleobjectid !=0 && (dragsingleobjectid != resizeObjId || levelsingleid != resizeObjId)){
         					$("<div title='Warning'>Please resolve the overlapping of components (marked in Red) before proceeding further !</div>").dialog();
         					break;
         				}
                	  	  var topfirst = screenchildren[firstLevel]['children'][secondLevel].portraitY;
      	                  var leftfirst = screenchildren[firstLevel]['children'][secondLevel].portraitX;
      	                  var widthfirst = screenchildren[firstLevel]['children'][secondLevel].portrait_width;
      	                  var heightfirst = screenchildren[firstLevel]['children'][secondLevel].portrait_height;
          	               
      	                if(secondLevel != dragobjid){
      	                	if(isOverlap(topfirst+12,topsec+12,leftfirst+12,leftsec+12,widthfirst+12,eval(widthsec)+12,heightfirst+12,eval(heightsec)+12)) //is overlapping
      	                	{
      	                		$('#' + dragobjid).css({
      	                	          border: "1px solid red"
      	                	        });
      	                		$('#' + secondLevel).css({
      	              	          border: "1px solid red"
      	              	        });
      	                		overlappingsingle = true;
                                dragsingleobjectid = dragobjid;
                                levelsingleid = secondLevel;
                                AxeControllerScope.overlapping = true;
      	                		//$("<div title='Warning'>The componenets are too close,please give more space before proceeding further !</div>").dialog();
      	                		break;
      	                	}
      	                }
                	}
                  	else { //if multilevel container like accordion
                  		for (var thirdLevel in screenchildren[firstLevel]['children'][secondLevel]['children']) {
                      		var topfirst = screenchildren[firstLevel]['children'][secondLevel]['children'][thirdLevel].portraitY;
            	            var leftfirst = screenchildren[firstLevel]['children'][secondLevel]['children'][thirdLevel].portraitX;
            	            var widthfirst = screenchildren[firstLevel]['children'][secondLevel]['children'][thirdLevel].portrait_width;
            	            var heightfirst = screenchildren[firstLevel]['children'][secondLevel]['children'][thirdLevel].portrait_height;
                            
            	            if(overlappingmulti == true && dragmultiobjectid !=0 && (dragmultiobjectid == resizeObjId|| levelmultiid == resizeObjId)){
            	            	$('#' + levelmultiid).css({
	          	                	border: "none"
	          	      	       	});
	      	                  
	          	              	$('#' + dragmultiobjectid).css({
	          	                	border: "none"
	          	              	});

	          	              overlappingmulti = false;
	          	              dragmultiobjectid = 0;
	          	              levelmultiid =0;
	          	              AxeControllerScope.overlapping = false;
               				}else if(overlappingmulti == true && dragmultiobjectid !=0 && (dragmultiobjectid != resizeObjId || levelmultiid != resizeObjId)){
               					$("<div title='Warning'>Please resolve the overlapping of components (marked in Red) before proceeding further !</div>").dialog();
               					break;
               				}
            	            if(thirdLevel != dragobjid){
          	                	if(isOverlap(topfirst+12,topsec+12,leftfirst+12,leftsec+12,widthfirst+12,eval(widthsec)+12,heightfirst+12,eval(heightsec)+12)) //is overlapping
          	                	{
          	                		$('#' + dragobjid).css({
          	                	          border: "1px solid red"
          	                	        });
          	                		$('#' + thirdLevel).css({
          	              	          border: "1px solid red"
          	              	        });
          	                		overlappingmulti = true;
  		          	              	dragmultiobjectid = dragobjid;
  		          	              	levelmultiid = thirdLevel;
  		          	              	AxeControllerScope.overlapping = true;
          	                		//$("<div title='Warning'>The componenets are too close,please give more space before proceeding further !</div>").dialog();
          	                		break;
          	                	}
          	                }
                  		}
                  	}
  	            }//out for
        	}else{
        		// drag object is on screen
        		var dragid = resizeObjId;
       			var draggedObj = screenchildren[dragid];
        			if(draggedObj != undefined){
        				if(overlappingscreen == true && dragscreenobjectid !=0 && (dragscreenobjectid == resizeObjId|| levelscreenid == resizeObjId)){
        				 $('#' + levelscreenid).css({
                          	border: "none"
                	        });
                          $('#' + dragscreenobjectid).css({
                          	border: "none"
                	        });
                          overlappingscreen = false;
                          dragscreenobjectid = 0;
                          AxeControllerScope.overlapping = false;
        				}else if(overlappingscreen == true && dragscreenobjectid !=0 && (dragscreenobjectid != resizeObjId || levelscreenid != resizeObjIdId)){
        					$("<div title='Warning'>Please resolve the overlapping of components (marked in Red) before proceeding further !</div>").dialog();
        					break;
        				}
                           
        	    		var topsec = draggedObj.portraitY;
        	            var leftsec = draggedObj.portraitX;
        	            var widthsec = draggedObj.portrait_width;
        	            var heightsec = draggedObj.portrait_height;
        	            
                    	var topfirst = screenchildren[firstLevel].portraitY;
                        var leftfirst = screenchildren[firstLevel].portraitX;
                        var widthfirst = screenchildren[firstLevel].portrait_width;
                        var heightfirst = screenchildren[firstLevel].portrait_height;
                       
                        if(firstLevel != dragid){
                        	if(isOverlap(topfirst+12,topsec+12,leftfirst+12,leftsec+12,widthfirst+12,eval(widthsec)+12,heightfirst+12,eval(heightsec)+12)) //is overlapping
                        	{
                        		$('#' + dragid).css({
                        	          border: "1px solid red"
                        	        });
                        		$('#' + firstLevel).css({
                      	          border: "1px solid red"
                      	        });
                        		dragscreenobjectid = dragid;
                        		levelscreenid = firstLevel;
                        		overlappingscreen = true;
                        		AxeControllerScope.overlapping = true;
                        		//$("<div title='Warning'>The componenets are too close,please give more space before proceeding further !</div>").dialog();
                        		break;
                        	}
                        }
        			}
        	}
        }
    }
    
    function isOverlap(topOne,topTwo,leftOne,leftTwo,widthOne,widthTwo,heightOne,heightTwo){
        /* var leftTop = leftTwo > leftOne && leftTwo < leftOne+widthOne 
                 && topTwo > topOne && topTwo < topOne+heightOne,
             rightTop = leftTwo+widthTwo > leftOne && leftTwo+widthTwo < leftOne+widthOne 
                 && topTwo > topOne && topTwo < topOne+heightOne,
             leftBottom = leftTwo > leftOne && leftTwo < leftOne+widthOne 
                 && topTwo+heightTwo > topOne && topTwo+heightTwo < topOne+heightOne,
             rightBottom = leftTwo+widthTwo > leftOne && leftTwo+widthTwo < leftOne+widthOne 
                 && topTwo+heightTwo > topOne && topTwo+heightTwo < topOne+heightOne;
         return leftTop || rightTop || leftBottom || rightBottom;*/
   	  
   	  var x1 = leftOne;
         var y1 = topOne;
         var h1 = heightOne;
         var w1 = widthOne;
         var b1 = y1 + h1;
         var r1 = x1 + w1;
         var x2 = leftTwo;
         var y2 = topTwo;
         var h2 = heightTwo;
         var w2 = widthTwo;
         var b2 = y2 + h2;
         var r2 = x2 + w2;

         if (b1 < y2 || y1 > b2 || r1 < x2 || x1 > r2) return false;
         return true;
     }
}