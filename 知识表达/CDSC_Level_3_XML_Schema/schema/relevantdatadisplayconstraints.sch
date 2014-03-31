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
	queryBinding="xslt" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

	<sch:ns uri="http://www.w3.org/2001/XMLSchema-instance" prefix="xsi" />

	<sch:pattern id="CheckActionIsDataDisplayRequest">
		<sch:title>Checks all actions are DataDisplayRequest</sch:title>
		<sch:rule
			context="CDSKnowledgeDocument/knowledgeModule[metadata/implementation/knowledgeType='Relevant Data Presentation']/action">
			<sch:assert test="@xsi:type ='DataDisplayRequest'">
				Relevant Data Presentation knowledge module only allows DataDisplayRequest
				actions
      </sch:assert>
		</sch:rule>
	</sch:pattern>

	<sch:pattern id="CheckOnlyTermConsraint">
		<sch:title>Checks all behaviors are of type TermCondition</sch:title>
		<sch:rule
			context="CDSKnowledgeDocument/knowledgeModule[metadata/implementation/knowledgeType='Relevant Data Presentation']/behavior">
			<sch:assert test="@xsi:type ='ConstraintTerm'">Only constraint term allowed as behavior
				for Relevant Data Presentation      
      </sch:assert>
		</sch:rule>
	</sch:pattern>



</sch:schema>   