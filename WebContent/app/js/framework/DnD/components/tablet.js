function TabletComponents(element_id) {
  var node, element, isMenuItem, menuItemId;

  switch (element_id) {
    case 'icon-label':
      isMenuItem = true;
      menuItemId = 'icon-label';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <label data-role="label" class="_Gp_element" id="' + id + '">Label</label> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-button':
      isMenuItem = true;
      menuItemId = 'icon-button';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <button type="button" data-role="button" class="_Gp_element btn btn-default" id="' + id + '">Button</button> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-camera':
      isMenuItem = true;
      menuItemId = 'icon-camera';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <button type="button" data-role="camera" class="_Gp_element ionic button button-light" id="' + id + '"> \
                  <i class="fa fa-camera"></i> \
                  <span>Take Photo</span> \
                </button> \
              </div>';
      $(node).appendTo('.axe');
      id++;
      element = $('#' + id)[0];

      break;
      
    case 'icon-recorder':
        isMenuItem = true;
        menuItemId = 'icon-recorder';
        node = '<div class="resizable" id="' + (id + 1) + '"> \
          	<img src="app/img/icons/ionic/record.png" data-role="recorder" class="image-default" id="' + id + '">\
          </div>';
        $(node).appendTo('.axe');
        id++;
        element = $('#' + id)[0];

        break;
    case 'icon-video':
        isMenuItem = true;
        menuItemId = 'icon-video';
        node = '<div class="resizable" id="' + (id + 1) + '"> \
          	<img src="app/img/icons/ionic/video.png" data-role="video" class="image-default" id="' + id + '">\
          </div>';
        $(node).appendTo('.axe');
        id++;
        element = $('#' + id)[0];

        break;    
    case 'icon-checkbox':
      isMenuItem = true;
      menuItemId = 'icon-checkbox';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <label class="_Gp_element form-checkbox" data-role="checkbox" id="' + id + '"> \
                  <input type="checkbox"> \
                  <span class="text">Option</span> \
                </label> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-radio':
      isMenuItem = true;
      menuItemId = 'icon-radio';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
		  					<label class="_Gp_element form-radio" data-role="radio" id="' + id + '"> \
			  					<input type="radio"> \
				  				<span class="text">Option</span> \
					  		</label> \
						  </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-input':
      isMenuItem = true;
      menuItemId = 'icon-input';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
		  					<input data-role="input" type="text" class="_Gp_element form-control" id="' + id + '"> \
			  			</div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-date':
      isMenuItem = true;
      menuItemId = 'icon-date';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <div data-role="date" class="_Gp_element date input-group" id="' + id + '"> \
                      <input type="text" class="form-control"> \
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      $('#' + id).datepicker();
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-select':
      isMenuItem = true;
      menuItemId = 'icon-select';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <select data-role="select" class="_Gp_element form-control" id="' + id + '"></select> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-border-container':
      isMenuItem = true;
      menuItemId = 'icon-border-container';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
		  					<div data-role="border-container" class="_Gp_element border-container" id="' + id + '"></div> \
			  			</div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-tab':
      isMenuItem = true;
      menuItemId = 'icon-tab';
      node = '<div class="resizable" id="' + (id + 4) + '"> \
                <div data-role="tab" id="' + id + '" class="_Gp_element tab"> \
                  <ul data-role="tab-list" class="tab-list nav nav-tabs"> \
                    <li class="active"> \
                      <a data-ng-href="#' + (id + 1) + '">Tab 1</a> \
                    </li> \
                    <li> \
                      <a data-ng-href="#' + (id + 2) + '">Tab 2</a> \
                    </li> \
                    <li> \
                      <a data-ng-href="#' + (id + 3) + '">Tab 3</a> \
                    </li> \
                  </ul> \
                  <div data-role="tab-content" class="tab-content"> \
                    <div data-role="section" class="section active" id="' + (id + 1) + '"></div> \
                    <div data-role="section" class="section" id="' + (id + 2) + '"></div> \
                    <div data-role="section" class="section" id="' + (id + 3) + '"></div> \
                  </div> \
                </div> \
              </div>';
      $(node).appendTo('.axe');
      id += 4;
      element = $('#' + id)[0];

      break;
    case 'icon-rich-text-editor':
      isMenuItem = true;
      menuItemId = 'icon-rich-text-editor';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <div class="_Gp_element rich-text-editor" data-role="rich-text-editor" id="' + id + '"> \
                    <div class="toolbar ql-toolbar ql-snow" id="_toolbar_' + id + '"> \
                        <span class="ql-format-group"> \
                            <select title="Font" class="ql-font"> \
                                <option value="sans-serif" selected="">Sans Serif</option> \
                                <option value="serif">Serif</option> \
                                <option value="monospace">Monospace</option> \
                            </select> \
                            <select title="Size" class="ql-size"> \
                                <option value="10px">Small</option> \
                                <option value="13px" selected="">Normal</option> \
                                <option value="18px">Large</option> \
                                <option value="32px">Huge</option> \
                            </select> \
                        </span> \
                        <span class="ql-format-group"> \
                            <span title="Bold" class="ql-format-button ql-bold"></span> \
                            <span class="ql-format-separator"></span> \
                            <span title="Italic" class="ql-format-button ql-italic"></span> \
                            <span class="ql-format-separator"></span> \
                            <span title="Underline" class="ql-format-button ql-underline"></span> \
                            <span class="ql-format-separator"></span> \
                            <span title="Strikethrough" class="ql-format-button ql-strike"></span> \
                        </span> \
                        <span class="ql-format-group"> \
                            <select title="Text Color" class="ql-color"> \
                                <option value="rgb(0, 0, 0)" label="rgb(0, 0, 0)" selected=""></option> \
                                <option value="rgb(230, 0, 0)" label="rgb(230, 0, 0)"></option> \
                                <option value="rgb(255, 153, 0)" label="rgb(255, 153, 0)"></option> \
                                <option value="rgb(255, 255, 0)" label="rgb(255, 255, 0)"></option> \
                                <option value="rgb(0, 138, 0)" label="rgb(0, 138, 0)"></option> \
                                <option value="rgb(0, 102, 204)" label="rgb(0, 102, 204)"></option> \
                                <option value="rgb(153, 51, 255)" label="rgb(153, 51, 255)"></option> \
                                <option value="rgb(255, 255, 255)" label="rgb(255, 255, 255)"></option> \
                                <option value="rgb(250, 204, 204)" label="rgb(250, 204, 204)"></option> \
                                <option value="rgb(255, 235, 204)" label="rgb(255, 235, 204)"></option> \
                                <option value="rgb(255, 255, 204)" label="rgb(255, 255, 204)"></option> \
                                <option value="rgb(204, 232, 204)" label="rgb(204, 232, 204)"></option> \
                                <option value="rgb(204, 224, 245)" label="rgb(204, 224, 245)"></option> \
                                <option value="rgb(235, 214, 255)" label="rgb(235, 214, 255)"></option> \
                                <option value="rgb(187, 187, 187)" label="rgb(187, 187, 187)"></option> \
                                <option value="rgb(240, 102, 102)" label="rgb(240, 102, 102)"></option> \
                                <option value="rgb(255, 194, 102)" label="rgb(255, 194, 102)"></option> \
                                <option value="rgb(255, 255, 102)" label="rgb(255, 255, 102)"></option> \
                                <option value="rgb(102, 185, 102)" label="rgb(102, 185, 102)"></option> \
                                <option value="rgb(102, 163, 224)" label="rgb(102, 163, 224)"></option> \
                                <option value="rgb(194, 133, 255)" label="rgb(194, 133, 255)"></option> \
                                <option value="rgb(136, 136, 136)" label="rgb(136, 136, 136)"></option> \
                                <option value="rgb(161, 0, 0)" label="rgb(161, 0, 0)"></option> \
                                <option value="rgb(178, 107, 0)" label="rgb(178, 107, 0)"></option> \
                                <option value="rgb(178, 178, 0)" label="rgb(178, 178, 0)"></option> \
                                <option value="rgb(0, 97, 0)" label="rgb(0, 97, 0)"></option> \
                                <option value="rgb(0, 71, 178)" label="rgb(0, 71, 178)"></option> \
                                <option value="rgb(107, 36, 178)" label="rgb(107, 36, 178)"></option> \
                                <option value="rgb(68, 68, 68)" label="rgb(68, 68, 68)"></option> \
                                <option value="rgb(92, 0, 0)" label="rgb(92, 0, 0)"></option> \
                                <option value="rgb(102, 61, 0)" label="rgb(102, 61, 0)"></option> \
                                <option value="rgb(102, 102, 0)" label="rgb(102, 102, 0)"></option> \
                                <option value="rgb(0, 55, 0)" label="rgb(0, 55, 0)"></option> \
                                <option value="rgb(0, 41, 102)" label="rgb(0, 41, 102)"></option> \
                                <option value="rgb(61, 20, 102)" label="rgb(61, 20, 102)"></option> \
                            </select> \
                            <span class="ql-format-separator"></span> \
                            <select title="Background Color" class="ql-background"> \
                                <option value="rgb(0, 0, 0)" label="rgb(0, 0, 0)"></option> \
                                <option value="rgb(230, 0, 0)" label="rgb(230, 0, 0)"></option> \
                                <option value="rgb(255, 153, 0)" label="rgb(255, 153, 0)"></option> \
                                <option value="rgb(255, 255, 0)" label="rgb(255, 255, 0)"></option> \
                                <option value="rgb(0, 138, 0)" label="rgb(0, 138, 0)"></option> \
                                <option value="rgb(0, 102, 204)" label="rgb(0, 102, 204)"></option> \
                                <option value="rgb(153, 51, 255)" label="rgb(153, 51, 255)"></option> \
                                <option value="rgb(255, 255, 255)" label="rgb(255, 255, 255)" selected=""></option> \
                                <option value="rgb(250, 204, 204)" label="rgb(250, 204, 204)"></option> \
                                <option value="rgb(255, 235, 204)" label="rgb(255, 235, 204)"></option> \
                                <option value="rgb(255, 255, 204)" label="rgb(255, 255, 204)"></option> \
                                <option value="rgb(204, 232, 204)" label="rgb(204, 232, 204)"></option> \
                                <option value="rgb(204, 224, 245)" label="rgb(204, 224, 245)"></option> \
                                <option value="rgb(235, 214, 255)" label="rgb(235, 214, 255)"></option> \
                                <option value="rgb(187, 187, 187)" label="rgb(187, 187, 187)"></option> \
                                <option value="rgb(240, 102, 102)" label="rgb(240, 102, 102)"></option> \
                                <option value="rgb(255, 194, 102)" label="rgb(255, 194, 102)"></option> \
                                <option value="rgb(255, 255, 102)" label="rgb(255, 255, 102)"></option> \
                                <option value="rgb(102, 185, 102)" label="rgb(102, 185, 102)"></option> \
                                <option value="rgb(102, 163, 224)" label="rgb(102, 163, 224)"></option> \
                                <option value="rgb(194, 133, 255)" label="rgb(194, 133, 255)"></option> \
                                <option value="rgb(136, 136, 136)" label="rgb(136, 136, 136)"></option> \
                                <option value="rgb(161, 0, 0)" label="rgb(161, 0, 0)"></option> \
                                <option value="rgb(178, 107, 0)" label="rgb(178, 107, 0)"></option> \
                                <option value="rgb(178, 178, 0)" label="rgb(178, 178, 0)"></option> \
                                <option value="rgb(0, 97, 0)" label="rgb(0, 97, 0)"></option> \
                                <option value="rgb(0, 71, 178)" label="rgb(0, 71, 178)"></option> \
                                <option value="rgb(107, 36, 178)" label="rgb(107, 36, 178)"></option> \
                                <option value="rgb(68, 68, 68)" label="rgb(68, 68, 68)"></option> \
                                <option value="rgb(92, 0, 0)" label="rgb(92, 0, 0)"></option> \
                                <option value="rgb(102, 61, 0)" label="rgb(102, 61, 0)"></option> \
                                <option value="rgb(102, 102, 0)" label="rgb(102, 102, 0)"></option> \
                                <option value="rgb(0, 55, 0)" label="rgb(0, 55, 0)"></option> \
                                <option value="rgb(0, 41, 102)" label="rgb(0, 41, 102)"></option> \
                                <option value="rgb(61, 20, 102)" label="rgb(61, 20, 102)"></option> \
                            </select> \
                        </span> \
                        <span class="ql-format-group"> \
                            <span title="List" class="ql-format-button ql-list"></span> \
                            <span class="ql-format-separator"></span> \
                            <span title="Bullet" class="ql-format-button ql-bullet"></span> \
                            <span class="ql-format-separator"></span> \
                            <select title="Text Alignment" class="ql-align"> \
                                <option value="left" label="Left" selected=""></option> \
                                <option value="center" label="Center"></option> \
                                <option value="right" label="Right"></option> \
                                <option value="justify" label="Justify"></option> \
                            </select> \
                        </span> \
                        <span class="ql-format-group"> \
                            <span title="Link" class="ql-format-button ql-link"></span> \
                        </span> \
                    </div> \
                    <div class="editor ql-container ql-snow" id="_editor_' + id + '"></div> \
                </div> \
            </div>';
      $(node).appendTo('.axe');
      new Quill('#_editor_' + id, {
        theme: 'snow',
        modules: {
          'link-tooltip': true,
          'toolbar': {
            container: '#_toolbar_' + id
          }
        }
      });

      id += 1;
      element = $('#' + id)[0];

      break;
    case 'icon-accordion':
      isMenuItem = true;
      menuItemId = 'icon-accordion';
      node = '<div class="resizable" id="' + (id + 4) + '"> \
                <div data-role="accordion" class="_Gp_element accordion panel-group" id="' + id + '"> \
                  <div class="panel panel-default"> \
                    <div data-role="panel-heading" class="panel-heading"> \
                      <h4 class="panel-title"> \
                        <a data-ng-href="#' + (id + 1) + '">Item #1</a> \
                      </h4> \
                    </div> \
                    <div data-role="section" class="section" id="' + (id + 1) + '"></div> \
                  </div> \
                  <div class="panel panel-default"> \
                    <div data-role="panel-heading" class="panel-heading"> \
                      <h4 class="panel-title"> \
                        <a data-ng-href="#' + (id + 2) + '">Item #2</a> \
                      </h4> \
                    </div> \
                    <div data-role="section" class="section" id="' + (id + 2) + '"></div> \
                  </div> \
                  <div class="panel panel-default"> \
                    <div data-role="panel-heading" class="panel-heading"> \
                      <h4 class="panel-title"> \
                        <a data-ng-href="#' + (id + 3) + '">Item #3</a> \
                      </h4> \
                    </div> \
                    <div data-role="section" class="section" id="' + (id + 3) + '"></div> \
                  </div> \
                </div> \
              </div>';
      $(node).appendTo('.axe');
      id += 4;
      element = $('#' + id)[0];

      /* trigger click on the first tab */
      $('#' + id).find('.panel').first().find('a').click();

      break;
    case 'icon-table':
      isMenuItem = true;
      menuItemId = 'icon-table';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <table data-role="table" class="_Gp_element table table-bordered" id="' + id + '"> \
                  <thead> \
                    <tr> \
                      <th>Col 1</th> \
                      <th>Col 2</th> \
                      <th>Col 3</th> \
                    </tr> \
                  </thead> \
                  <tbody> \
                    <tr> \
                      <td></td> \
                      <td></td> \
                      <td></td> \
                    </tr> \
                    <tr> \
                      <td></td> \
                      <td></td> \
                      <td></td> \
                    </tr> \
                    <tr> \
                      <td></td> \
                      <td></td> \
                      <td></td> \
                    </tr> \
                  </tbody> \
                </table> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-horizontal-line':
      isMenuItem = true;
      menuItemId = 'icon-horizontal-line';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <hr data-role="hrule" class="_Gp_element horizontal-line" id="' + id + '"> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-vertical-line':
      isMenuItem = true;
      menuItemId = 'icon-vertical-line';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
				  			<hr data-role="vrule" class="_Gp_element vertical-line" id="' + id + '"> \
				  		</div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-panel':
      isMenuItem = true;
      menuItemId = 'icon-panel';
      node = '<div class="resizable" id="' + (id + 2) + '"> \
                <div data-role="panel" class="_Gp_element panel panel-default" id="' + id + '"> \
                  <div data-role="panel-heading" class="panel-heading"> \
                    <h3 class="panel-title">Panel title</h3> \
                  </div> \
                  <div data-role="panel-content" class="panel-body" id="' + (id + 1) + '"></div> \
                </div> \
              </div>';
      $(node).appendTo('.axe');
      id += 2;
      element = $('#' + id)[0];

      break;
    /* Boopathi */
    case 'icon-link':
      isMenuItem = true;
      menuItemId = 'icon-link';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
		    				<a data-href="#" data-role="link" class="_Gp_element" id="' + id + '">Link</a> \
				    	</div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-image':
      isMenuItem = true;
      menuItemId = 'icon-image';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <div data-role="image" class="_Gp_element image" id="' + id + '">\
                  <img src="app/img/icons/icon-image-view.svg" class="image-default">\
                </div>\
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    default:
      isMenuItem = false;
  }

  return {
    element: element,
    isMenuItem: isMenuItem,
    menuItemId: menuItemId
  };
}