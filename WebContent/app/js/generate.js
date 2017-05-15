/* TODO: update generate functionality */
var example = {
    "screen": {
        "id": 0,
        "name": "iOS",
        "description": "",
        "label": "",
        "type": "screen",
        "notes": "",
        "width": 0,
        "height": 0,
        "children": {
            "1": {
                "id": 1,
                "name": "",
                "description": "",
                "label": "",
                "notes": "",
                "parent": "screen",
                "parent_name": "",
                "screen_id": 0,
                "number_of_children": "",
                "type": "label",
                "supports_label": "",
                "ui_technology": "",
                "is_container": "",
                "data_binding_context": false,
                "verb_binding_context": false,
                "events": "",
                "event_verb_combo": "",
                "verb_target": "",
                "width": 37,
                "height": 30,
                "portraitX": 48,
                "portraitY": 26,
                "landscapeX": 48,
                "landscapeY": 26
            }
        },
        "client_device_type_id": 0,
        "client_device_type": "",
        "client_device_type_name": "",
        "client_device_type_label": "",
        "client_device_type_description": "",
        "client_device_screen_size": "",
        "client_device_resolution": "",
        "client_device_ppcm": "",
        "client_device_type_os_name": "",
        "client_device_type_os_version": "",
        "landscape_image_name": "",
        "portrait_image_name": "",
        "orientation": "portrait",
        "orientation_locked": "",
        "client_language_type": "",
        "client_library_type": "",
        "activity_id": 0,
        "projectid": 0,
        "primary_noun_id": 0,
        "secondary_noun_ids": [],
        "human_language_id": 0
    }
};

var json = JSON.stringify(example);
var nodeTree = JSON.parse(json);

function GenerateLevel(id, parent, type, width, height, rows, columns, offsetX, offsetY) {
    var html;

    switch (type) {
        case 'label': {
            html = '<label class="_Gp_element" id="' + id + '">Label</label>';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'button': {
            html = '<button type="button" class="_Gp_element btn btn-default" id="' + id + '">Button</button>';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'checkbox': {
            html = '<label class="_Gp_element" id="' + id + '"> \
                        Option \
                        <input type="checkbox"> \
                    </label>';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'radio': {
            html = '<label class="_Gp_element" id="' + id + '"> \
                        Option \
                        <input type="radio"> \
                    </label>';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height,
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'input': {
            html = '<input type="text" class="_Gp_element form-control" id="' + id + '">';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'date': {
            html = '<input type="text" class="_Gp_element form-control" data-date-format="mm/dd/yy" id="' + id + '" >';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            }).datepicker();
            break;
        }
        case 'select': {
            html = '<select class="_Gp_element form-control" id="' + id + '"></select>';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'rich-text-editor': {
            html = '<div class="_Gp_element rich-text-editor" data-role="rich-text-editor" id="' + id + '"> \
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
                    </div>';
            $('#' + parent).append(html);
            createRichTextEditor(id);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height,
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'table': {
            html = '<table class="_Gp_element table table-bordered" id="' + id + '">';
            for (var i = 0; i < rows; ++i) {
                html += '<tr>';
                for (var j = 0; j < columns; ++j) {
                    html += '<td></td>';
                }
                html += '</tr>';
            }
            html += '</table>';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'hrule': {
            html = '<hr class="_Gp_element horizontal-line" id="' + id + '">';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
        case 'vrule': {
            html = '<hr class="_Gp_element vertical-line" id="' + id + '">';
            $('#' + parent).append(html);
            $('#' + id).css({
                position: 'absolute',
                width: width,
                height: height
            }).offset({
                left: Math.round($('#' + parent).offset().left) + offsetX,
                top: Math.round($('#' + parent).offset().top) + offsetY
            });
            break;
        }
    }
}

function GenerateApp(nodeTree) {
    var firstLevel, secondLevel, thirdLevel;
    var html, id, parent, children, type, width, height,
        portraitX, portraitY, parentOffsetX, parentOffsetY,
        rows, columns;

    for (firstLevel in nodeTree['screen']['children']) {
        if (nodeTree['screen']['children'].hasOwnProperty(firstLevel)) {
            id = firstLevel;
            parent = nodeTree['screen']['children'][firstLevel]['parent'];
            type = nodeTree['screen']['children'][firstLevel]['type'];
            width = nodeTree['screen']['children'][firstLevel]['width'];
            height = nodeTree['screen']['children'][firstLevel]['height'];
            portraitX = nodeTree['screen']['children'][firstLevel]['portraitX'];
            portraitY = nodeTree['screen']['children'][firstLevel]['portraitY'];
            if (nodeTree['screen']['children'][firstLevel]['rows'] !== undefined)
                rows = nodeTree['screen']['children'][firstLevel]['rows'];
            if (nodeTree['screen']['children'][firstLevel]['columns'] !== undefined)
                columns = nodeTree['screen']['children'][firstLevel]['columns'];

            GenerateLevel(id, parent, type, width, height, rows, columns, portraitX, portraitY);
            switch (type) {
                case 'border-container': {
                    html = '<div class="_Gp_element border-container" id="' + id + '"></div>';
                    $('#' + parent).append(html);
                    $('#' + id).css({
                        position: 'absolute',
                        width: width,
                        height: height
                    }).offset({
                        left: Math.round($('#' + parent).offset().left) + portraitX,
                        top: Math.round($('#' + parent).offset().top) + portraitY
                    });

                    /* nested elements */
                    if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'])) {
                        for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
                            if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                                id = secondLevel;
                                parent = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['parent'];
                                type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['type'];
                                width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['width'];
                                height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['height'];
                                parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['parentOffsetX'];
                                parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['parentOffsetY'];
                                if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['rows'] !== undefined)
                                    rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['rows'];
                                if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['columns'] !== undefined)
                                    columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['columns'];

                                GenerateLevel(id, parent, type, width, height, rows, columns, parentOffsetX, parentOffsetY);
                            }
                        }
                    }

                    break;
                }
                case 'tab': {
                    html = '<div id="' + id + '" class="_Gp_element tab"> \
								<ul class="tab-list nav nav-tabs"> \
									<li class="active"> \
										<a href="#' + (parseInt(id, 10) - 3) + '">Tab 1</a> \
									</li> \
									<li> \
										<a href="#' + (parseInt(id, 10) - 2) + '">Tab 2</a> \
									</li> \
									<li> \
										<a href="#' + (parseInt(id, 10) - 1) + '">Tab 3</a> \
									</li> \
								</ul> \
								<div class="tab-content"> \
									<div class="tab-panel active" id="' + (parseInt(id, 10) - 3) + '"></div> \
									<div class="tab-panel" id="' + (parseInt(id, 10) - 2) + '"></div> \
									<div  class="tab-panel" id="' + (parseInt(id, 10) - 1) + '"></div> \
								</div> \
							</div> ';
                    $('#' + parent).append(html);
                    $('#' + id).css({
                        position: 'absolute',
                        width: width,
                        height: height
                    }).offset({
                        left: Math.round($('#' + parent).offset().left) + portraitX,
                        top: Math.round($('#' + parent).offset().top) + portraitY
                    });

                    for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
                        if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                            /* nested elements */
                            if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'])) {
                                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                                    if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                                        id = thirdLevel;
                                        parent = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['parent'];
                                        type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'];
                                        width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['width'];
                                        height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['height'];
                                        parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['parentOffsetX'];
                                        parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['parentOffsetY'];
                                        if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'] !== undefined)
                                            rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'];
                                        if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'] !== undefined)
                                            columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'];

                                        GenerateLevel(id, parent, type, width, height, rows, columns, parentOffsetX, parentOffsetY);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case 'accordion': {
                    html = '<div class="_Gp_element accordion panel-group" id="' + id + '"> \
                                <div class="panel panel-default active"> \
                                    <div class="panel-heading"> \
                                        <h4 class="panel-title"> \
                                            <a href="#' + (parseInt(id, 10) - 3) + '">Item #1</a> \
                                        </h4> \
                                    </div> \
                                    <div class="accordion-panel" id="' + (parseInt(id, 10) - 3) + '"></div> \
                                </div> \
                                <div class="panel panel-default"> \
                                    <div class="panel-heading"> \
                                        <h4 class="panel-title"> \
                                            <a href="#' + (parseInt(id, 10) - 2) + '">Item #2</a> \
                                        </h4> \
                                    </div> \
                                    <div class="accordion-panel" id="' + (parseInt(id, 10) - 2) + '"></div> \
                                </div> \
                                <div class="panel panel-default"> \
                                    <div class="panel-heading"> \
                                        <h4 class="panel-title"> \
                                            <a href="#' + (parseInt(id, 10) - 1) + '">Item #3</a> \
                                        </h4> \
                                    </div> \
                                    <div class="accordion-panel" id="' + (parseInt(id, 10) - 1) + '"></div> \
                                </div> \
                            </div>';
                    $('#' + parent).append(html);
                    $('#' + id).css({
                        position: 'absolute',
                        width: width,
                        height: height
                    }).offset({
                        left: Math.round($('#' + parent).offset().left) + portraitX,
                        top: Math.round($('#' + parent).offset().top) + portraitY
                    });
                    for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
                        if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                            $('#' + secondLevel).css('height', nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['height']);
                            /* nested elements */
                            if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'])) {
                                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                                    if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                                        id = thirdLevel;
                                        parent = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['parent'];
                                        type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'];
                                        width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['width'];
                                        height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['height'];
                                        parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['parentOffsetX'];
                                        parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['parentOffsetY'];
                                        if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'] !== undefined)
                                            rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'];
                                        if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'] !== undefined)
                                            columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'];

                                        GenerateLevel(id, parent, type, width, height, rows, columns, parentOffsetX, parentOffsetY);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case 'panel': {
                    html = '<div class="_Gp_element panel panel-default" id="' + id + '"> \
								<div class="panel-heading"> \
									<h3 class="panel-title">Panel title</h3> \
								</div> \
								<div class="panel-body"></div> \
							</div>';
                    $('#' + parent).append(html);
                    $('#' + id).css({
                        position: 'absolute',
                        width: width,
                        height: height
                    }).offset({
                        left: Math.round($('#' + parent).offset().left) + portraitX,
                        top: Math.round($('#' + parent).offset().top) + portraitY
                    });

                    /* nested elements */
                    if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'])) {
                        for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
                            if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                                id = secondLevel;
                                parent = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['parent'];
                                type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['type'];
                                width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['width'];
                                height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['height'];
                                parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['parentOffsetX'];
                                parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['parentOffsetY'];
                                if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['rows'] !== undefined)
                                    rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['rows'];
                                if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['columns'] !== undefined)
                                    columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['columns'];

                                GenerateLevel(id, parent, type, width, height, rows, columns, parentOffsetX, parentOffsetY);
                            }
                        }
                    }
                    break;
                }
            }
        }
    }
}

GenerateApp(nodeTree);