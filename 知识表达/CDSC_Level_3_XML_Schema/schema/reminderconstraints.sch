<?xml version="1.0" encoding="UTF-8"?>
<!--
     Copyright © 2011 Brigham and Women's Hospital, Inc. All rights reserved.

     Support Acknowledgment: This material is derived from work supported
     under a contract with the Agency for Healthcare Research and Quality (AHRQ),
     an agency of the Department of Health and Human Services (Contract No. HHSA290200810010).
     AHRQ supports health services research that will improve the quality of health
     care and promote evidence-based decision making.

     THIS SOFTWARE IS PROVIDED BY THE BRIGHAM AND WOMEN'S HOSPITAL, INC.
     "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
     TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
     PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE
     LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
     DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
     SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
     CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
     OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
     USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 -->
<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron"
	queryBinding="xslt">


	<sch:title>Modality-specific constraints for reminders</sch:title>

	<sch:ns uri="http://www.w3.org/2001/XMLSchema-instance" prefix="xsi" />
	<sch:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema" />
	<sch:ns prefix="fn" uri="http://www.w3.org/2005/xpath-functions" />

  	<sch:pattern name="At least one action has to occur">
		<sch:rule context="CDSKnowledgeDocument/knowledgeModule[metadata/implementation/knowledgeType='Alert or Reminder']">
			<sch:assert test="count(./action)>0">At least one action must occur
			</sch:assert>
		</sch:rule>
	</sch:pattern>

<sch:pattern name="At least one ConstraintCondition">
		<sch:rule context="CDSKnowledgeDocument/knowledgeModule[metadata/implementation/knowledgeType='Alert or Reminder']">
			<sch:assert test="count(behavior[@xsi:type ='ConstraintCondition'])>0">
			At least one constraint condition must be present
			</sch:assert>
		</sch:rule>
	</sch:pattern>


  <sch:pattern name="There is at least on ConstraintCondition that has as constraintType Trigger">
		<sch:rule context="CDSKnowledgeDocument/knowledgeModule[metadata/implementation/knowledgeType='Alert or Reminder']">
			<sch:assert test="count(behavior[constraintType ='Trigger'])>0">
			At least one ConstraintCondition  of type Trigger
			</sch:assert>
		</sch:rule>
	</sch:pattern>   
	

<sch:pattern name="At least one MessageRequest">
		<sch:rule context="CDSKnowledgeDocument/knowledgeModule[metadata/implementation/knowledgeType='Alert or Reminder']">
			<sch:assert test="count(action[@xsi:type ='MessageRequest'])>0">
			For each reminder or alert at least one action of type MessageRequest should occur
			</sch:assert>
		</sch:rule>
	</sch:pattern>


</sch:schema>






