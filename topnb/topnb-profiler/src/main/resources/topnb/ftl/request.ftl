
                    <div class="span12">                    
                        <div class="head clearfix">
                            <div class="isw-grid"></div>
                            <h1>请求耗时</h1>
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                <tr >
                                	<th></th>
                                    <th colspan="3" style="text-align: center;">总请求</th>
                                    <th colspan="3" style="text-align: center;" title="耗时超过50ms的请求">慢请求</th>
                                    <th colspan="3" style="text-align: center;" title="耗时超过500ms的请求">特慢请求</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                <tr>
                                    <th width="30">NO.</th>
                                    
                                    <th width="60">数量</th>
                                    <th width="60">总耗时</th>
                                    <th width="60">平均</th>
                                    
                                    <th width="60">数量</th>
                                    <th width="60">总耗时</th>
                                    <th width="60">平均</th>
                                    
                                    <th width="60">数量</th>
                                    <th width="60">总耗时</th>
                                    <th width="60">平均</th>
                                    
                                    <th width="150">URL</th>
       
                                    <th style="white-space: nowrap;">描述</th>
                                </tr>
                                </thead>
                                <tbody>
	                            	<#if paging?? && paging?size gt 0>
	                            	<#list paging as requestDto>
	                                <tr>
	                                    <td>${requestDto_index+1}</td>
	                                    
	                                    <td>${requestDto.allCount}</td>
	                                    <td>${time(requestDto.allTime)}</td>
	                                    <td>${avgTime(requestDto.allCount,requestDto.allTime)}</td>
	                                    
	                                    <td>${requestDto.slowCount}</td>
	                                    <td>${time(requestDto.slowTime)}</td>
	                                    <td>${avgTime(requestDto.slowCount,requestDto.slowTime)}</td>
	                                    
	                                    <td>${requestDto.verySlowCount}</td>
	                                    <td>${time(requestDto.verySlowTime)}</td>
	                                    <td>${avgTime(requestDto.verySlowCount,requestDto.verySlowTime)}</td>
	                                    
	                                    <td style="white-space: nowrap;">${requestDto.url}</td>
	                                    
	                                    
	                                    <td></td>
	                                </tr>
	                                </#list>
	                            	<#else>
	                                <tr>
	                                    <td colspan="12" align="center">没有记录</td>
	                                </tr>
	                    			</#if>
	                            </tbody>
                            </table>
                        </div>
                    </div>  