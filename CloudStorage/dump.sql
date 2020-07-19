PGDMP         3                x            storage    11.5    11.5                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            	           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            
           1262    16402    storage    DATABASE     �   CREATE DATABASE storage WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1251' LC_CTYPE = 'English_United States.1251';
    DROP DATABASE storage;
             postgres    false                       0    0    DATABASE storage    ACL     )   GRANT ALL ON DATABASE storage TO nordic;
                  postgres    false    2826            �            1259    16403    user    TABLE     �   CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_email character(50) NOT NULL,
    user_name character(50) NOT NULL,
    user_password character(255) NOT NULL,
    user_role character(50),
    user_tariff integer
);
    DROP TABLE public."user";
       public         postgres    false                       0    0    TABLE "user"    ACL     6   GRANT SELECT,UPDATE ON TABLE public."user" TO nordic;
            public       postgres    false    196            �            1259    16408    Users_user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Users_user_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public       postgres    false    196                      0    16403    user 
   TABLE DATA               g   COPY public."user" (user_id, user_email, user_name, user_password, user_role, user_tariff) FROM stdin;
    public       postgres    false    196   �                  0    0    Users_user_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public."Users_user_id_seq"', 2, true);
            public       postgres    false    197            �
           2606    16407    user Users_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT "Users_pkey" PRIMARY KEY (user_id);
 =   ALTER TABLE ONLY public."user" DROP CONSTRAINT "Users_pkey";
       public         postgres    false    196            �
           1259    24601    fki_user_tariff    INDEX     I   CREATE INDEX fki_user_tariff ON public."user" USING btree (user_tariff);
 #   DROP INDEX public.fki_user_tariff;
       public         postgres    false    196            �
           2606    24596    user user_tariff    FK CONSTRAINT     }   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_tariff FOREIGN KEY (user_tariff) REFERENCES public.tariff(tariff_id);
 <   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_tariff;
       public       postgres    false    196               �   x�3�LL���s �z���
��#H1
���%���8�U�[y�:FTx%y�e�g�e��DG�U�'��yzGy�z��d���P&M����gIjq�� 2r�C��It4z
s<K3�M�Ҳ�B�<rL���<�s}�=3��2-##˂<�
S|��=r�]I�jP����"���'F��� >c�O     