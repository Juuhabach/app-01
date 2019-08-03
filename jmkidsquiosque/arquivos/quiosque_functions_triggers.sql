
-- Criar estar triggers e functions só na base do quiosque




CREATE OR REPLACE FUNCTION public.alterou_campo (
  anterior pg_catalog.anyelement,
  novo pg_catalog.anyelement
)
RETURNS boolean AS
$body$
DECLARE
   v_aux boolean := false;
   
BEGIN
   if ((anterior is null) and (novo is not null)) then
      v_aux := true;
   elseif ((anterior is not null) and (novo is null)) then
      v_aux := true;
   elseif ((anterior is not null) and (novo is not null) and (anterior <> novo)) then
      v_aux := true;
   end if;

   RETURN v_aux;
END;
$body$
LANGUAGE 'plpgsql'
IMMUTABLE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;

COMMENT ON FUNCTION public.alterou_campo(anterior pg_catalog.anyelement, novo pg_catalog.anyelement)
IS 'Retorna true se houve qualquer alteração no campo quanto ao seu valor anterior e o novo valor';

ALTER FUNCTION public.alterou_campo (anterior pg_catalog.anyelement, novo pg_catalog.anyelement)
  OWNER TO jmkids;


  
  
  
  
  
  
  
  
  
  
CREATE OR REPLACE FUNCTION public.valores_iguais (
  campo1 pg_catalog.anyelement,
  campo2 pg_catalog.anyelement
)
RETURNS boolean AS
$body$
DECLARE
   v_aux boolean := false;
   
BEGIN
   if ((campo1 is null) and (campo2 is null)) then
      v_aux := true;
   elseif ((campo1 is not null) and (campo2 is null)) then
      v_aux := false;
   elseif ((campo1 is null) and (campo2 is not null)) then
      v_aux := false;   
   elseif ((campo1 is not null) and (campo2 is not null) and (campo1 = campo2)) then
      v_aux := true;
   else
      v_aux := false;   
   end if;

   RETURN v_aux;
END;
$body$
LANGUAGE 'plpgsql'
IMMUTABLE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;

COMMENT ON FUNCTION public.valores_iguais(campo1 pg_catalog.anyelement, campo2 pg_catalog.anyelement)
IS 'Retorna true se os campos possuem o mesmo conteúdo ou ambos são null. Se não retorna false.';

ALTER FUNCTION public.valores_iguais (campo1 pg_catalog.anyelement, campo2 pg_catalog.anyelement)
  OWNER TO jmkids;  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  




CREATE OR REPLACE FUNCTION quiosque.brinquedooperacao_aiud (
)
RETURNS trigger AS
$body$
BEGIN
   -- -----------------------------------------------
   -- OPERAÇÕES COMUNS A INSERT E UPDATE
   -- -----------------------------------------------
   IF ((TG_OP = 'INSERT') or (TG_OP = 'UPDATE')) THEN      
        
       
   END IF;
   -- ------------------------
   -- OPERAÇÕES SÓ PARA INSERT
   -- ------------------------
   IF (TG_OP = 'INSERT') THEN
      INSERT INTO quiosque.caixamovimento(
            id, descricao, operacao, 
			valor, versao, caixa_id, 
			quiosque_id, brinquedooperacao_id, formapagamento_id, datahora)
	  VALUES (nextval('quiosque.caixamovimentoid'), 'Pré-Pago - Recebido na contratação de brinquedo', 'Entrada', 
	         new.valorprepago, 0, new.caixa_id, 
			 new.quiosque_id, new.id, new.formaprepago_id, current_timestamp);
          
      RETURN NEW;  
   -- ---------------------------
   -- OPERAÇÕES SÓ PARA UPDATE
   -- ---------------------------
   ELSEIF (TG_OP = 'UPDATE') THEN   
          
		  
		IF (public.alterou_campo(old.valorprepago, new.valorprepago)) THEN
			IF (old.valorprepago = 0 AND new.valorprepago > 0) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pré-Pago - Recebimento na ativação de brinquedo', 'Entrada', 
				 new.valorprepago, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formaprepago_id);	  
			ELSEIF (old.valorprepago > 0 AND new.valorprepago = 0) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pré-Pago - Estorno ref. ativação de brinquedo', 'Saida', 
				 old.valorprepago, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, old.formaprepago_id);	 
            ELSEIF (old.valorprepago > new.valorprepago) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pré-Pago - Alterado valor de recebimento ref. ativação de brinquedo', 'Saida', 
				 (old.valorprepago - new.valorprepago), 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, old.formaprepago_id);	  				 
			ELSEIF (old.valorprepago < new.valorprepago) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pré-Pago - Alterado valor de recebimento ref. ativação de brinquedo', 'Entrada', 
				 (new.valorprepago - old.valorprepago), 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formaprepago_id);	  				 	 
			END IF;			
        END IF;
		
		  
		  
		  
	    IF (public.alterou_campo(old.valorpospago, new.valorpospago)) THEN
			IF (old.valorpospago = 0 AND new.valorpospago > 0) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Recebimento na inativação de brinquedo', 'Entrada', 
				 new.valorpospago, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formapospago_id);	  
			ELSEIF (old.valorpospago > 0 AND new.valorpospago = 0) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Estorno ref. inativação de brinquedo', 'Saida', 
				 old.valorpospago, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, old.formapospago_id);	 
            ELSEIF (old.valorpospago > new.valorpospago) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Alterado valor de recebimento ref. inativação de brinquedo', 'Saida', 
				 (old.valorpospago - new.valorpospago), 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, old.formapospago_id);	  				 
			ELSEIF (old.valorpospago < new.valorpospago) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Alterado valor de recebimento ref. inativação de brinquedo', 'Entrada', 
				 (new.valorpospago - old.valorpospago), 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formapospago_id);	  				 	 
			END IF;			
        END IF;
		
		
		
	    IF (public.alterou_campo(old.valordesconto, new.valordesconto)) THEN
			IF (old.valordesconto = 0 AND new.valordesconto > 0) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Desconto na inativação de brinquedo', 'Saida', 
				 new.valordesconto, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formapospago_id);	  
			ELSEIF (old.valordesconto > 0 AND new.valordesconto = 0) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Estorno desconto ref. inativação de brinquedo', 'Entrada', 
				 old.valordesconto, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, old.formapospago_id);	 
            ELSEIF (old.valordesconto > new.valordesconto) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Alterado valor de desconto ref. inativação de brinquedo', 'Entrada', 
				 (old.valordesconto - new.valordesconto), 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, old.formapospago_id);	  				 
			ELSEIF (old.valordesconto < new.valordesconto) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Alterado valor de recebimento ref. inativação de brinquedo', 'Saida', 
				 (new.valordesconto - old.valordesconto), 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formapospago_id);	  				 	 
			END IF;			
        END IF;		
		
		
		
		
		
	    IF (public.alterou_campo(old.valorcreditoconcedido, new.valorcreditoconcedido)) THEN
			IF (old.valorcreditoconcedido = 0 AND new.valorcreditoconcedido > 0) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Crédito na inativação de brinquedo', 'Saida', 
				 new.valorcreditoconcedido, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formapospago_id);	  
			ELSEIF (old.valorcreditoconcedido > 0 AND new.valorcreditoconcedido = 0) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Estorno crédito ref. inativação de brinquedo', 'Entrada', 
				 old.valorcreditoconcedido, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, old.formapospago_id);	 
            ELSEIF (old.valorcreditoconcedido > new.valorcreditoconcedido) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Alterado valor de crédito ref. inativação de brinquedo', 'Entrada', 
				 (old.valorcreditoconcedido - new.valorcreditoconcedido), 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, old.formapospago_id);	  				 
			ELSEIF (old.valorcreditoconcedido < new.valorcreditoconcedido) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pós-Pago - Alterado valor de crédito ref. inativação de brinquedo', 'Saida', 
				 (new.valorcreditoconcedido - old.valorcreditoconcedido), 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formapospago_id);	  				 	 
			END IF;			
        END IF;		
		
		
		
		
		
		
		IF (public.alterou_campo(old.datahoracancelamento, new.datahoracancelamento)) THEN
		
		    IF (old.datahoracancelamento is null AND new.datahoracancelamento is not null) then	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pré-Pago - Cancelamento de brinquedo', 'Saida', 
				 old.valorprepago, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formaprepago_id);	 
		    ELSEIF (old.datahoracancelamento is not null AND new.datahoracancelamento is null) THEN	  
			  INSERT INTO quiosque.caixamovimento(
				id, descricao, operacao, 
				valor, versao, caixa_id, datahora,
				quiosque_id, brinquedooperacao_id, formapagamento_id)
			  VALUES (nextval('quiosque.caixamovimentoid'), 'Pré-Pago - Estorno de Cancelamento de brinquedo', 'Entrada', 
				 old.valorprepago, 0, new.caixa_id, current_timestamp,
				 new.quiosque_id, new.id, new.formaprepago_id);	 
			END IF;  		
			
        END IF;
		
      RETURN NEW;  
   -- ---------------------------   
   -- OPERAÇÕES SÓ PARA DELETE
   -- ---------------------------   
   ELSEIF (TG_OP = 'DELETE') THEN   
      
      
      RETURN OLD;
   END IF;
END;
$body$
LANGUAGE 'plpgsql'
VOLATILE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;







ALTER FUNCTION quiosque.brinquedooperacao_aiud ()
  OWNER TO jmkids;  
   
CREATE TRIGGER brinquedooperacao_aiud AFTER INSERT OR UPDATE OR DELETE 
   ON quiosque.brinquedooperacao FOR EACH ROW 
   EXECUTE PROCEDURE quiosque.brinquedooperacao_aiud();
      
   

   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
CREATE OR REPLACE FUNCTION quiosque.brinquedooperacao_biud (
)
RETURNS trigger AS
$body$
BEGIN   
   -- -----------------------------------------------
   -- OPERAÇÕES COMUNS A INSERT E UPDATE
   -- -----------------------------------------------
   IF ((TG_OP = 'INSERT') or (TG_OP = 'UPDATE')) THEN       
        
       
   END IF;
   -- ------------------------
   -- OPERAÇÕES SÓ PARA INSERT
   -- ------------------------
   IF (TG_OP = 'INSERT') THEN
      new.log = to_char(new.datahoracontratado, 'dd/MM/yyyy HH24:MI:SS')||E' - Data e hora da contratado. \n';
	  
      RETURN NEW;  
   -- ---------------------------
   -- OPERAÇÕES SÓ PARA UPDATE
   -- ---------------------------
   ELSEIF (TG_OP = 'UPDATE') THEN   
      -- Logs das operações
      if (public.alterou_campo(old.datahoraativacao, new.datahoraativacao)) then
         new.log = new.log||to_char(new.datahoraativacao, 'dd/MM/yyyy HH24:MI:SS')||E' - Alterou data e hora da ativacao. \n';
      end if;  
      if (public.alterou_campo(old.datahoracancelamento, new.datahoracancelamento)) then
         new.log = new.log||to_char(new.datahoracancelamento, 'dd/MM/yyyy HH24:MI:SS')||E' - Alterou data e hora de cancelamento. \n';
      end if;  
      if (public.alterou_campo(old.datahoracontratado, new.datahoracontratado)) then
         new.log = new.log||to_char(new.datahoracontratado, 'dd/MM/yyyy HH24:MI:SS')||E' - Alterou data e hora contratado. \n';
      end if;  
      if (public.alterou_campo(old.datahoraempausa, new.datahoraempausa)) then
	     if (new.datahoraempausa is not null) then 
            new.log = new.log||to_char(new.datahoraempausa, 'dd/MM/yyyy HH24:MI:SS')||E' - Alterou data e hora em pausa. \n';
		 else
            new.log = new.log||to_char(current_timestamp, 'dd/MM/yyyy HH24:MI:SS')||E' - Alterou data e hora em pausa. \n';
         end if; 		 
      end if;  
      if (public.alterou_campo(old.datahorainativacao, new.datahorainativacao)) then
         new.log = new.log||to_char(new.datahorainativacao, 'dd/MM/yyyy HH24:MI:SS')||E' - Alterou data e hora de inativação. \n';
      end if;  
          
          
      RETURN NEW;  
   -- ---------------------------   
   -- OPERAÇÕES SÓ PARA DELETE
   -- ---------------------------   
   ELSEIF (TG_OP = 'DELETE') THEN   
      
      
      RETURN OLD;
   END IF;
END;
$body$
LANGUAGE 'plpgsql'
VOLATILE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;


  
  
  
  
  
  
ALTER FUNCTION quiosque.brinquedooperacao_biud ()
  OWNER TO jmkids;    
   
CREATE TRIGGER brinquedooperacao_biud
  BEFORE INSERT OR UPDATE OR DELETE 
  ON quiosque.brinquedooperacao
FOR EACH ROW 
  EXECUTE PROCEDURE quiosque.brinquedooperacao_biud();
   


   
   
   
   
   
   
   
   